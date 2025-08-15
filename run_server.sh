#!/bin/bash
set -e

trap 'echo "Something went wrong at $LINENO. Exiting..."; exit 1' ERR

if [ -f .env ]; then
  read -p ".env exists. Overwrite? (y/N): " confirm
  [[ $confirm != [yY] ]] && echo "Cancelled." && exit 0
fi

read -p "Enter port: " port

read -p "Enter your OpenAI key: " api_key

echo

cat <<EOF > .env
HOST_PORT=$port
HOST_URL=0.0.0.0
OPENAI_THINGY=$api_key
EOF

echo
cat .env
echo

if [ ! -x gradlew ]; then
  echo "Making gradlew executable..."
  chmod +x gradlew
fi

echo "Building with Gradle..."
./gradlew clean buildFatJar

echo "Creating log files..."
mkdir -p logs
touch logs/recipes.log logs/tokens.log

echo "Running with Docker..."
docker compose up --wait

echo
echo "Success :)"
echo
docker ps
