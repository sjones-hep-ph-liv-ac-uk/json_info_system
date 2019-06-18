#!/usr/bin/python

import json

import getopt, sys

def pretty(d, indent=0):
  for key, value in d.items():
    print('  ' * indent + str(key))
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
      sys.exit()
    elif opt in ("-j", "--json"):
      jsonFile = arg
    else:
      assert False, "unhandled option"

  with open (jsonFile, "r") as file:
    x = file.read() # .replace('\n', '')
  
  y = json.loads(x)
  pretty (y)

  
  # the result is a Python dictionary:
  #print(y["age"]) 

if __name__ == "__main__":
    main()


