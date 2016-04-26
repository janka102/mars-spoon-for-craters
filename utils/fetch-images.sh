#!/bin/bash

# ./fetch-images.sh
# Run this to download all the grayscale altimeter tiles along with the color
# tiles. This will then merge the grayscale tiles on to the alpha channel of the
# color tiles.

# Dependancies: imagemagick

set -e

command -v convert >/dev/null 2>&1 || { echo >&2 "Imagemagick does not appear to be installed. Aborting."; exit 1; }

export alpha='Mars_MGS_MOLA_DEM_mosaic_global_463m_8'
export color='Mars_Viking_MDIM21_ClrMosaic_global_232m'
export apiURL="https://api.nasa.gov/mars-wmts/catalog/{$alpha,$color}/1.0.0//default/default028mm/5/%d/%d.png"

download() { # row col
  set -e

  filename="$1-$2.png"

  echo %{http_code} %{filename_effective} %{size_download} %{time_total}\\n | \
    curl -sS --create-dirs -w "@-" -o "#1/$filename" $(printf $apiURL $1 $2)

  convert "$color/$filename" \( "$alpha/$filename" -colorspace gray -alpha off \) -compose copy-opacity -composite "final/$filename"

  return 0
}
export -f download

mkdir -p altimeter && cd altimeter

mkdir -p final $alpha $color

for row in `seq 0 31`; do
  for col in `seq 0 63`; do
    echo $row $col
  done
done | xargs -n 2 -P 8 -I {} bash -c 'download $@' _ {}
