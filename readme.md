# Lara's Pipes

Pipes that are actually pleasant to use!

Adds pipes to Minecraft for transferring, filtering, and sorting items and fluids.

![Demo Front](https://raw.githubusercontent.com/larashores/laraspipes/main/images/demo-front.png)

This is my first mod so it has a few quirks (e.g. there is no way to sever the connection between pipes). If you like it please let me know and I can consider new features!

## Supported Versions

This mod runs on the following Minecraft versions.
- [Forge 1.20.1](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html)

## Summary

### Items

![Item Demo](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-demo.png)

This mod adds Item Extractors and Depositors to Minecraft. Pipes are used to connect Item Extractors and Item
Depositors. They are stylized to look like glass pneumatic tubes. When an Extractor is connected to a Depositor through Pipes, the Extractor will attempt to remove items
from its attached inventory (if any) and move them through the attached Depositor to its attached inventory (if any). 

Item Depositors can have "filters" configured. See the Item Depositor section for more details. If an Extractor
is connected to a Depositor with filters, only items matching the filters will be transferred.

Item Extractors can be connected to multiple Depositors. This can be used to create an item "sorter". A single Extractor
can connect to multiple Depositors. Each Depositor can be configured with a type of items (e.g. blocks, tools, plants).
This way items can be dropped of in a single inventory connected to an Item Extractor, and they will be automatically be
distributed to the correct inventory through each Item Depositor and its filters.

If an Item Extractor is connected to multiple Item Depositors, items will be routed to the closest Item Depositor first,
and only the second if the first inventory is full. The exception to this is Item Depositors without filters. Items will
only be routed to Item Depositors without filters if there are no Item Depositors with filters that can accept the item.
This can be used to create an "extra" inventory, that accepts items only if there are no configured Item Depositors with
a matching filter that can accept the item. 

### Fluids

![Fluid Demo](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-demo.png)

_Tanks from the [Railcraft](https://www.curseforge.com/minecraft/mc-mods/railcraft-reborn) mod_

Fluid Extractors, Fluid Depositors, and Fluid Pipes work similarly to their item counterparts. However, they currently
do not support filtering. Fluid pipes are stylized to look like lead plumbing pipes. Fluids will be drained at a rate
of 1 bucket per second for each tank being drained.

## Blocks

### Item Extractor

Item Extractors are used for removing items from inventories. The large side of the extractor must face the inventory to
extract items from. If an extractor is placed next to an inventory, it will auto-face the inventory regardless of the
player direction. Item Pipes can be connected to the Extractor from any other direction.

![Item Extractor Left](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-extractor-left.png)
![Item Extractor Right](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-extractor-right.png)

#### Recipe

Item Extractors are made in a crafting table with the following recipe.
- 1 x Hopper
- 1 x Redstone Block
- 4 x Glass

![Item Extractor Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-extractor-recipe.png)


### Item Depositor

Item Depositors are used for adding items to inventories. The large side of the extractor must face the inventory to add
items to. If an extractor is placed next to an inventory, it will auto-face the inventory regardless of the player
direction. Item Pipes can be connected to the Extractor from any other direction.

![Item Depositor Left](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-left.png)
![Item Depositor Right](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-right.png)

#### Menu

Right-clicking an Item Depositor will open up a menu where item "filters" can be selected. Items from the player's
inventory can be "copied" into the Item Depositor slots to set the filters. If at least one filter is set, only items
matching the filters can be added to the Item Depositor's inventory. For example, only Cobblestone, Oak Planks, and
Redstone blocks can be transferred through the below Item Depositor. If no filters are selected, all types of items can
be transferred.

![Item Depositor Menu](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-menu.png)

#### Recipe

Item Depositors are made in a crafting table with the following recipe.
- 1 x Hopper
- 1 x Redstone Block
- 4 x Glass

![Item Depositor Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-recipe.png)


### Item Pipe

Item Pipes are used for connecting Item Extractors and Item Depositors. They can be chained to connect
Item Depositors and Item Extractors at any distance. When connected, Item Extractors will attempt to move items through any
attached Item Depositors.

![Item Pipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-pipe.png)

#### Recipe

Item Pipes are made in a crafting table with the following recipe.
- 1 x Redstone
- 2 x Glass

![Item Pipe Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-pipe-recipe.png)

### Fluid Extractor

Fluid Extractors are used for removing fluids from tanks. The large side of the extractor must face the tank to
extract fluids from. If an extractor is placed next to a tank, it will auto-face the tank regardless of the
player direction. Fluid Pipes can be connected to the Extractor from any other direction.

![Fluid Extractor Left](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-extractor-left.png)
![Fluid Extractor Right](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-extractor-right.png)

#### Recipe

Fluid Extractors are made in a crafting table with the following recipe.
- 1 x Hopper
- 1 x Redstone Block
- 4 x Iron Ingot

![Fluid Extractor Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-extractor-recipe.png)


### Fluid Depositor

Fluid Depositors are used for adding fluid to tanks. The large side of the extractor must face the tank to add
fluids to. If an extractor is placed next to a tank, it will auto-face the tank regardless of the player
direction. Fluid Pipes can be connected to the Extractor from any other direction.

![Fluid Depositor Left](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-depositor-left.png)
![Fluid Depositor Right](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-depositor-right.png)

#### Recipe

Fluid Depositors are made in a crafting table with the following recipe.
- 1 x Hopper
- 1 x Redstone Block
- 4 x Iron Ingot

![Fluid Depositor Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-depositor-recipe.png)


### Fluid Pipe

Fluid Pipes are used for connecting Fluid Extractors and Fluid Depositors. They can be chained to connect
Fluid Depositors and Fluid Extractors at any distance. When connected, Fluid Extractors will attempt to move fluids through any
attached Fluid Depositors.

![Fluid Pipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-pipe.png)

#### Recipe

Item Pipes are made in a crafting table with the following recipe.
- 1 x Redstone
- 2 x Iron Ingot

![Fluid Pipe Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/fluid-pipe-recipe.png)

## Development

To develop this mod using Intellij, run the `forgegradle runs/genIntellijRuns` gradle task. This will create the
following additional gradle tasks:

- `runClient`
  - This task will run Minecraft with the mod installed. This way the mod can be tested directly in-game.
- `runData`
  - This will regenerate data files from the datagen configuration.
- `runServer`

The following gradle tasks may also be useful:

- `build/build`
  - This will compile the mod into a `.jar` file and place it into the `build/libs` folder.
- `documentation/javadoc`
  - This will update the HTML documentation from the scanned javadocs.

## Future Plans

- Add power pipes.
- Add configuration option for item/fluid transfer speeds.
- Add block for emptying fluid from Fluid Depositors into buckets.
- Add ability to disconnect pipes.
- Allow Item Depositors to auto-sort deposited items.

## Links

- Curseforge: https://www.curseforge.com/minecraft/mc-mods/laras-pipes
- Github: https://github.com/larashores/laraspipes
- Docs: https://larashores.github.io/laraspipes