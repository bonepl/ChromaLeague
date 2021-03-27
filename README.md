# Chroma League
Java open-source Razer Chroma keyboard integration for League of Legends.

If you like this project, consider giving me a tip for all the hard work :)

[![paypal](https://www.paypalobjects.com/en_US/PL/i/btn/btn_donateCC_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=5JFBXY66RT8Z6&item_name=Chroma+League&currency_code=PLN)

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

If you have any comments, suggestions, ideas, found a bug or just want to say hello please visit official thread at Razer Insider: [Chroma League - League of Legends integration for Razer Chroma](https://insider.razer.com/index.php?threads/chroma-league-league-of-legends-integration-for-razer-chroma.65412/).

## Overview
![Chroma League HUD](https://github.com/bonepl/ChromaLeague/blob/master/doc/images/ChromaLeague.png "Chroma League HUD")

This is what basic in game HUD looks like on Chroma Keyboard.
Certain in-game events will spawn additional animations.

## Requirements
* Windows
* League of Legends
* Razer Synapse 3
* Chroma enabled keyboard

## Running
Simply download [the latest Chroma League release](https://github.com/bonepl/ChromaLeague/releases/latest), unpack and run it
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

You can use [this PowerShell script](https://github.com/bonepl/ChromaLeague/blob/master/chroma-league-launcher/src/scripts/restartRazerSdk.ps1)
to restart the service.

If you encounter a bug, please attach logs (`cl.log`) with the exceptions
to help me track the error.

Before reporting a bug, please check Chroma League's issues page if
it isn't already worked on.

## Plans
Next plans include resolving any bugs/issues, crafting better, advanced animations for events (like dragon kills)
and then maybe extending support to other peripherals like mice or headphones.

## Disclaimer
**League of Legends** and all related logic used in this project 
are owned and copyrighted by [Riot Games](https://www.riotgames.com)

**Razer Chroma** and **Razer Chroma SDK** and all related logic used in this project
are owned and copyrighted by [Razer](https://www.razer.com/)

## Postscript
I'm constantly improving this project in my free time, 
so I cannot promise any timelines for next releases.

If you spot a bug, feel free to create a new issue on GitHub repository. 
