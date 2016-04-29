# Mars Spoon For Craters

![](https://s3.amazonaws.com/files.open.nasa.gov/spaceapps/media/eb16b8c0-fdb1-4404-91cd-170bd4a566f8.png)

This Minecraft Plugin pulls Altimeter Data and Color Images from NASA's API to create the surface of Mars in Minecraft! The entire surface of Mars can be crossed in this Minecraft map, from the landing spot of the Curiosity Rover, to the towering Olympous Mons, and then on to the poles covered with dry ice. Kids and adults alike can explore the planet like never before, getting a neat perspective on areas previously viewable only from satellite imagery and rover-captured snapshots. The nature of Minecraft encourages exploration, and the ability for players to place themselves on Mars will inspire aspiring astronauts and scientists to follow their hopes into space.

Each block scales to around 463 meters. The colors are matched with a number of clay and ice blocks to find the most similar block based on color. Additionally, the height of the block is determined from the grayscale altimeter data. The combined effect is an immersive world, completely generated from real data captured from Mars.

## Build

There is a prebuilt jar file in the releases, you can [download MarsSpoon](https://github.com/janka102/mars-spoon-for-craters/releases/latest).

Otherwise, building requires Java 8 and Apache ant. If on OS X, `brew install java ant`.

You can open the folder in Eclipse and build it in there, or alternatively from the command line.

```bash
git clone git@github.com:janka102/mars-spoon-for-craters.git
cd mars-spoon-for-craters/com.github.janka102.minecraft
ant
```

This places `MarsSpoon.jar` in `server/plugins/`. You can then run the server.

*NOTE:* The start script it set to remove the world data after it shuts down so that the world is regenerated when it starts back up

```bash
cd ../server
./start.sh
```

**Downloading the source images**

If you want to download and generate the merged altimeter and color tiles we use, there is a utility script.

```bash
cd utils
./fetch-images.sh
```
