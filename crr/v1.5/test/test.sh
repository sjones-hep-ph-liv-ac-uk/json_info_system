#!/bin/bash

# Test the json files I know about. The sed at the end makes it pretty. If the results are empty, you're good.
# BTW: jsonschema is a python tool you can install...

jsonschema -i liv.json ../crr/schema.json   2>&1  | sed -e "s/^/\n\n/" -e "s/,/,\n/g" > liv.results

jsonschema -i man.json ../crr/schema.json   2>&1  | sed -e "s/^/\n\n/" -e "s/,/,\n/g" > man.results

jsonschema -i vcycle.json ../crr/schema.json   2>&1  | sed -e "s/^/\n\n/" -e "s/,/,\n/g" > vcycle.results

