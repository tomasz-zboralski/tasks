#!/usr/bin/env bash

open_chrome() {
  /usr/bin/open -a "/Applications/Google Chrome.app" 'http://localhost:8080/crud/v1/task/getTasks'
}

fail() {
   echo "There were errors"
}

end() {
   echo "Work is finished"
}

if ./runcrud.sh; then
  open_chrome
else
  fail
fi