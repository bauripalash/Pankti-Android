echo "[1/5] Going to pankti/ directory"
cd pankti/
echo "[2/5]Starting to run Compile APIs with gomobile"
bash ./compileAndroidApi.sh
echo "[+] Finished Compiling API"
echo "[3/5]Moving Files"
echo "[4/5]Moving API .aar"
mv androidapi.aar ../app/libs/
echo "Moved API .aar "
echo "[5/5]Moving API .jar"
mv androidapi-sources.jar ../app/libs/
echo "[+]Moved API .jar"
cd ..

