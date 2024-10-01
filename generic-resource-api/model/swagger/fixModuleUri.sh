#!/bin/sh

# fixModuleUri.sh : rewrites swagger to insert module name at beginning
# of URI (e.g. /rests/operations/GENERIC-RESOURCE-API:service-topology-operation)
# and to outer "output" tag, to be consistent with OpenDaylight ideosyncracies
# arguments:
# $1 - module name
# $2 - swagger file name
#
# Note: this was tested to work with yaml version of swagger
mv $2 $2.backup
cat $2.backup | sed -e 's/\/operations\//\/operations\/'$1':/g' -e 's/\/data\//\/data\/'$1':/g' -e 's/output:/\"'$1':output\":/g'> $2
rm $2.backup
