#!/usr/bin/python

import json
import getopt, sys
import collections

class Dict(dict):
  def __init__(self, inp=None):
    if isinstance(inp,dict):
      super(Dict,self).__init__(inp)
    else:
      super(Dict,self).__init__()
      if isinstance(inp, (collections.Mapping, collections.Iterable)): 
        si = self.__setitem__
        for k,v in inp:
          si(k,v)

  def __setitem__(self, k, v):
    try:
      self.__getitem__(k)
      raise ValueError("duplicate key '{0}' found".format(k))
    except KeyError:
      super(Dict,self).__setitem__(k,v)

#global cris
#cris = []

def pretty(d, indent=0):
  print ("indent -- " + str(indent))
  for key, value in d.items():
    keyStr = str(key)

    print('  ' * indent) + keyStr

    if isinstance(value, dict):
      pretty(value, indent+1)
    else:
      if (isinstance(value, list)):
        sys.stdout.write('  ' * (indent+1) )
        for v in value:
          sys.stdout.write(v + ", ")
        sys.stdout.write('\n')
      else:
        print('  ' * (indent+1) + str(value))

def usage():
  print "Give a json filename as an argument, e.g. -f abc.json";

def main():
  try:
    opts, args = getopt.getopt(sys.argv[1:], "hj:v", ["help", "json="])
  except getopt.GetoptError as err:
    print str(err)  
    usage()
    sys.exit(2)
  jsonFile = None
  verbose = False
  for opt, arg in opts:
    if opt == "-v":
      verbose = True
    elif opt in ("-h", "--help"):
      usage()
      sys.exit(2)
    elif opt in ("-j", "--json"):
      jsonFile = arg
    else:
      assert False, "unhandled option"

  with open (jsonFile, "r") as file:
    x = file.read()
  #print x
 
  try: 
   # json.load(open('config.json'), object_pairs_hook=OrderedDict)
    #y = json.loads(x,object_pairs_hook=collections.OrderedDict)
    y = json.loads(x,object_pairs_hook=Dict)
  except Exception,e:
    print("Blew up " + str(e))
    sys.exit(3)
  print y


  #pretty (y)

  
  # the result is a Python dictionary:
  #print(y["age"]) 

if __name__ == "__main__":
    main()


