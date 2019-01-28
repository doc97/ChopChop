#!/bin/sh

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <PLATFORM>, <PLATFORM> = 'linux' or 'windows'"
    exit
fi

platform="${1}"
if [ "${platform}" != "linux" ] && [ "${platform}" != "windows" ]; then
    echo "Please choose either 'linux' or 'windows' as the platform"
    exit
fi

# variables
project_name=ChopChop
project_root=$(realpath ../../..)
export_base_dir=${project_root}/desktop/export
export_dir=${project_name}
export_name=${project_name}.jar
export_zip=${project_name}-$(date +%d-%b-%Y)-${platform}.zip

echo "====== Configuration ======"
echo "Project root directory: ${project_root}"
echo "Export base directory: ${export_base_dir}"
echo "Export output directory: ${export_dir}"
echo "Export file name: ${export_name}"
echo "Export zip name: ${export_zip}"
echo "==========================="
echo

cd ${project_root}
./gradlew desktop:dist

echo 
echo "========================="
echo 

cd ${export_base_dir}
if [ -d "${export_dir}" ]; then
    echo "Deleting output directory"
    rm -rf ${export_dir}
fi

echo "Creating output directory"
mkdir ${export_dir}

echo "Copying jar"
cp ../build/libs/desktop-1.0.jar ${export_dir}/${export_name}

echo "Copying other files"
cd ${export_base_dir}
cp include/README.txt ${export_dir}/

echo "Create exe file"
mkdir ${export_dir}/jre
if [ "${platform}" = "linux" ]; then
    cp -R jre-linux/* ${export_dir}/jre
    cp include/run.sh ${export_dir}/
    chmod +x ${export_dir}/run.sh
else
    cp -R jre-win32/* ${export_dir}/jre
    cd ${project_root}
    ./gradlew desktop:createExe
    rm ${export_base_dir}/${export_dir}/${export_name}
fi

echo "Zipping project"
cd ${export_base_dir}
zip -r ${export_zip} ${project_name} >/dev/null 2>&1

echo
echo "DONE!"

echo
echo "Zip has been exported to $(readlink -f ${export_zip})"
