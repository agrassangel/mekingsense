#!/bin/bash
wget https://github.com/mozilla/geckodriver/releases/download/v0.31.0/geckodriver-v0.31.0-linux64.tar.gz
tar xvfz geckodriver-v0.31.0-linux64.tar.gz
rm -rf geckodriver-v0.31.0-linux64.tar.gz
wget https://chromedriver.storage.googleapis.com/102.0.5005.27/chromedriver_linux64.zip
unzip chromedriver_linux64.zip
rm -rf chromedriver_linux64.zip

