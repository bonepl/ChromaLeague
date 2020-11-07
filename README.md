# ChromaLeague
Java open-source Razer Chroma keyboard integration for League of Legends.

## Introduction
Razer Chroma is a wonderful framework provided by [Razer](https://www.razer.com/)
for implementing custom led animations for their pheripherals.

Many applications/games have its official support but [League of Legends](https://leagueoflegends.com)
is not one of them.

So I designed my custom League of Legends Razer Chroma integration
that I'm using daily playing games on Summoner's Rift.

This end up with bigger project **Chroma League** which I'm sharing 
for all League of Legends players owning Razer Chroma keyboards to enjoy
the integrations

**Chroma League** is using League's [Live Client Data Api](https://developer.riotgames.com/docs/lol#game-client-api_live-client-data-api)
exposed during game to fetch current game's state and react to the in-game events.

## Requirements
* Windows
* League of Legends
* Synapse 3 with running Razer Chroma SDK Server service
* Chroma enabled keyboard

## Running
Simply download the latest **Chroma League** release, unpack and run it
by executing `chroma-league.bat`.

**Chroma League** will automatically detect when you join the game and start running integrations.

## Compatibility
* runs on Windows only
* supports only Chroma enabled keyboards 
(was tested on BlackWidow, but should support the others)
* developed for Summoner's Rift standard games 
(supports natively other game types, but there can be some side effects)

## Implemented integrations
- animated health bar (with health loss and gain animations)
- animated resource bar (and customized for all champions)
- level up animation
- gold pouch with animated coins (3000 gold means pouch is full)
- enemy/ally dragon/herald/baron kill indicators
- dragons killed by allies counter
- dragon soul indicator
- baron/elder buff indicator
- killing spree counter
- assist spree counter
- kill/assist animation
- respawn animation
- dead animation
- game victory animation
- game defeat animation
- dim background light for the keyboard for playing in the dark

## Troubleshooting
Double check if Razer Chroma SDK Server service in Windows (`services.msc`) is up and running.
Few times I experienced a bug where it was stuck in Paused state and not responding.

You can use [this PowerShell script](https://github.com/bonepl/ChromaLeague/blob/v1.0.0/chroma-league-launcher/src/scripts/restartRazerSdk.ps1)
to restart the service.

## Disclaimer
**League of Legends** and all related logic used in this project 
are owned and copyrighted by [Riot Games](https://www.riotgames.com)

**Razer Chroma** and **Razer Chroma SDK** and all related logic used in this project
are owned and copyrighted by [Razer](https://www.razer.com/)

## Postscript
I'm constantly improving this project in my free time, 
so I cannot promise any timelines for next releases.

If you spot a bug, feel free to create a new issue on GitHub repository. 