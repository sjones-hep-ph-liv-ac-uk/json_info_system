#!/usr/bin/python

import json
import getopt, sys
import collections
import ijson

def die(s):
  print 'Problem: ',s
  sys.exit(1)

def usage():
  print "Give a json filename as an argument, e.g. -f abc.json";

def getExtrinsics(bigList,smallList):
  # The contract is: all in small list must exist in big list.
  extrinsics = ""
  if (not bigList):
    return ""
  else:
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
      sys.exit(2)
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

  level = -1
  
  inArrayOfResourceVOs = False
  inArrayOfShareVOs = False
  inShares = False
  inAccessPoints = False

  resource = 'none'

  parser = ijson.parse(f)

  for prefix, event, value in parser:
    #print "\n\nEVENT: "
    #print "prefix: ",prefix
    #print "event: ",event
    #print "value: ",value

    # Only value allowed on level 0 is computingresources 
    if (level == 0):
      if (event == 'map_key'):
        if (value != 'computingresources'):
          die('First tag must be computingresources')

    # The resource name comes on level 1. Only thing to check is uniqueness
    if (level == 1):
      if (event == 'map_key'):
        resource = value
        if (resource in allResources):
          die("duplicate resource " + resource)

        # Starting a new resource, so set everything up
        allResources.append(resource)
        del resourceLevelVOs[:]
        del sharesLevelVOs[:]
        del resourceLevelShares[:]
        del resourceLevelAps[:]
        inArrayOfResourceVOs = False
        inArrayOfShareVOs = False
        inAccessPoints = False
        inShares = False
        inAccessPoints = False

    if (level == 2):
    
      # When exiting level 2, make sure no VOs in the shares are extrinsic to the VOs in the resource section (if any)
      if (event == 'end_map'):
        if (resourceLevelVOs):
          problemVO = getExtrinsics(resourceLevelVOs,sharesLevelVOs)
          if (len(problemVO) != 0):
            die ('inconsistent assigned VOs in ' + resource + ', check resource and share VOs for ' + problemVO )
      
      elif (event == 'map_key'):
        # Identify the section we are entering; assigned_vos, shares or accesspoints
        if (value == 'assigned_vos'):
          inArrayOfResourceVOs = True
          inAccessPoints = False
          inShares = False
          inAccessPoints = False
          inArrayOfShareVOs = False
        elif (value == 'shares'):
          inShares = True
          inAccessPoints = False
          inArrayOfResourceVOs = False
          inArrayOfShareVOs = False
        elif (value == 'accesspoints'):
          inAccessPoints = True
          inArrayOfResourceVOs = False
          inArrayOfShareVOs = False
          inShares = False

    if (level == 3):
      # make sure names are unique
      if (inShares):
        if (event == 'map_key'):
          share = value 
          if (share in resourceLevelShares):
            die("duplicate share " + share + " in resource " + resource)
          resourceLevelShares.append(share)

      if (inAccessPoints):
        if (event == 'map_key'):
          ap = value 
          if (ap in resourceLevelAps):
            die("duplicate accesspoint " + ap + " in resource " + resource)
          resourceLevelAps.append(ap)

    if (level == 4):
      # Find out if we should be collecting share level VOs
      if (event == 'map_key'):
        if (value == 'assigned_vos'):
          inArrayOfShareVOs = True
          inArrayOfResourceVOs = False
          inAccessPoints = False 

    # Collect resource level VOs
    if (inArrayOfResourceVOs):
      if (event == 'string'):
        resourceLevelVOs.append(value)

    # Collect share level VOs
    if (inArrayOfShareVOs):
      if (event == 'string'):
        sharesLevelVOs.append(value)

    # Finish any arrays
    if (event == 'end_array'):
      inArrayOfResourceVOs = False
      inArrayOfShareVOs = False

    # Work the level 
    if (event == 'start_map'):
      level = level + 1
    elif (event == 'end_map'):
      level = level - 1

if __name__ == "__main__":
    main()

