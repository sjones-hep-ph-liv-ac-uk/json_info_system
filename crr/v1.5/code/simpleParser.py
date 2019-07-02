#!/usr/bin/python
import getopt, sys
import ijson

def die(s):
  print 'Problem: ',s
  sys.exit(1)

def usage():
  print "Give a json filename as an argument, e.g. -j abc.json";

def getExtrinsics(bigList,smallList):
  # The contract is: all in small list must exist in big list.
  extrinsics = ""
  for small in smallList:
    itExists = False 
    for big in bigList:
      #print 'compare ' + small + ' ' + big        
      if (small == big):
        itExists = True
    if (itExists != True):
      extrinsics = extrinsics + small + " "
  return extrinsics

#----
def main():
  try:
    opts, args = getopt.getopt(sys.argv[1:], "hj:", ["help", "json="])
  except getopt.GetoptError as err:
    print str(err)  
    usage()
    sys.exit(2)
  jsonFile = None
  for opt, arg in opts:
    if opt in ("-h", "--help"):
      usage()
      sys.exit(3)
    elif opt in ("-j", "--json"):
      jsonFile = arg
    else:
      assert False, "unhandled option"

  f = open(jsonFile,"r")

  allResources = []
  resourceLevelVOs = []
  resourceLevelShares = []
  resourceLevelAps = []
  sharesLevelVOs = []
  apsLevelShares = []

  inArrayOfResourceVOs = False
  inArrayOfShareVOs = False
  inArrayOfApsShares = False
  inShares = False
  inAps = False

  parser = ijson.parse(f)

  currentLevel = -1
  currentResource = 'none'

  try:
    for prefix, event, value in parser:
  
      #print "\n\nEVENT: "
      #print "prefix: ",prefix
      #print "event: ",event
      #print "value: ",value
  
      # Only value allowed on level 0 is computingresources 
      if (currentLevel == 0):
        if (event == 'map_key'):
          if (value != 'computingresources'):
            die('First tag must be computingresources')
  
      # The resource name comes on level 1. Only thing to check is uniqueness.
      if (currentLevel == 1):
        if (event == 'map_key'):
          currentResource = value
          if (currentResource in allResources):
            die("duplicate resource " + currentResource)
  
          # Starting a new resource, so set everything up
          allResources.append(currentResource)
          del resourceLevelVOs[:]
          del sharesLevelVOs[:]
          del apsLevelShares[:]
          del resourceLevelShares[:]
          del resourceLevelAps[:]
  
          inArrayOfResourceVOs = False
          inArrayOfShareVOs = False
          inArrayOfApsShares = False
          inShares = False
          inAps = False
  
      if (currentLevel == 2):
      
        if (event == 'end_map'):
          # When exiting a resource make sure no VOs in the shares are extrinsic to the VOs in the resource section (if any)
          if (resourceLevelVOs):
            problemVOs = getExtrinsics(resourceLevelVOs,sharesLevelVOs)
            if (len(problemVOs) != 0):
              die ('inconsistent assigned VOs in resource ' + currentResource + ', check resource and share VOs for ' + problemVOs )
  
          # When exiting a resource make sure no shares in the aps are extrinsic to the shares in the resource section (if any)
          if (resourceLevelShares):
            problemShares = getExtrinsics(resourceLevelShares,apsLevelShares)
            if (len(problemShares) != 0):
              die ('inconsistent shares for resource ' + currentResource + ', check shares for ' + problemShares)
        
        elif (event == 'map_key'):
          # Identify the section we are entering; assigned_vos, shares or accesspoints
          if (value == 'assigned_vos'):
            inArrayOfResourceVOs = True
            inAps = False
            inArrayOfApsShares = False
            inArrayOfShareVOs = False
            inShares = False
          elif (value == 'shares'):
            inShares = True
            inAps = False
            inArrayOfApsShares = False
            inArrayOfResourceVOs = False
            inArrayOfShareVOs = False
          elif (value == 'accesspoints'):
            inAps = True
            inArrayOfApsShares = False
            inArrayOfResourceVOs = False
            inArrayOfShareVOs = False
            inShares = False
  
      if (currentLevel == 3):
        # make sure names are unique
        if (inShares):
          if (event == 'map_key'):
            share = value 
            if (share in resourceLevelShares):
              die("duplicate share " + share + " in resource " + currentResource)
            resourceLevelShares.append(share)
  
        if (inAps):
          if (event == 'map_key'):
            ap = value 
            if (ap in resourceLevelAps):
              die("duplicate accesspoint " + ap + " in resource " + currentResource)
            resourceLevelAps.append(ap)
  
      if (currentLevel == 4):
        # Find out if we should be collecting, e.g. share level VOs or aps level shares.
        if (event == 'map_key'):
          if (inShares):
            if (value == 'assigned_vos'):
              inArrayOfShareVOs = True
              inAps = False 
          elif (inAps):
            if (value == 'shares'):
              inArrayOfApsShares = True
              inShares = False
  
      if (inArrayOfResourceVOs):
        if (event == 'string'):
          resourceLevelVOs.append(value)
  
      if (inArrayOfShareVOs):
        if (event == 'string'):
          sharesLevelVOs.append(value)
  
      if (inArrayOfApsShares):
        if (event == 'string'):
          apsLevelShares.append(value)
  
      # Finish any arrays in one go so I don't need to woory
      if (event == 'end_array'):
        inArrayOfResourceVOs = False
        inArrayOfShareVOs = False
        inArrayOfApsShares = False
  
      # Work the level 
      if (event == 'start_map'):
        currentLevel = currentLevel + 1
      elif (event == 'end_map'):
        currentLevel = currentLevel - 1

  except ijson.backends.python.UnexpectedSymbol as e:
    print 'Some kind of parse error: ' + str(e)
    print 'Perhaps json file is not well formed.'
    print 'Please validate the json _before_ using this program.'
    sys.exit(4)

if __name__ == "__main__":
    main()

