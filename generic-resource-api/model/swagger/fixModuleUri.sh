#!/bin/sh

mv $2 $2.backup
cat $2.backup | sed -e 's/'$1':/_&/1' -e 's/\([^_]\)'$1':/\//g' -e 's/_\('$1':\)/\1/' > $2
rm $2.backup
