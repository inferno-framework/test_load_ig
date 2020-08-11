#!/bin/bash

docker build -t test_load_ig . && docker run -m 1000m -p 8080:4567 test_load_ig
