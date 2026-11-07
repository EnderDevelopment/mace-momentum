# Minecraft Plugin: Mace Momentum

Mace Momentum is a Minecraft plugin that adds a conditional mace cooldown: after a player’s first mace hit, they have five seconds to land another mace hit. If they do not, their mace attacks are blocked for 60 seconds.

## Install

1. Build the plugin with Java 21 and Maven by running `mvn package`.
2. Copy `target/plugin.jar` into your Paper server’s `plugins` folder.
3. Restart the server.

## Use

Attack an entity with a mace to start the five-second follow-up window. Land another mace hit within that window to prevent the cooldown. If you do not, mace attacks are blocked for 60 seconds. The plugin has no commands or configuration.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/28953d06-d6d8-4b6e-9578-d211de4ee7d7?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=28953d06-d6d8-4b6e-9578-d211de4ee7d7
