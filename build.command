rm -fR tmp-build
mkdir tmp-build
javac -extdirs lib/ -d tmp-build/ `find src/ | grep ".java"`
jar cf releases/amazon-products-r`ls -1 releases | wc -l`.jar -C tmp-build ./
