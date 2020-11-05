#!/bin/sh

echo '*TRACE*' sed -e 's/'$1':/_&/1' -e 's/\([^_]\)'$1':/\//g' -e 's/_\('$1':\)/\1/' -i '' $2
echo "*TRACE* sed is in $(which sed)"
sed -e 's/'$1':/_&/1' -e 's/\([^_]\)'$1':/\//g' -e 's/_\('$1':\)/\1/' -i '' $2
