# Lara's Pipes

Pipes that are actually pleasant to use!

Adds item pipes to Minecraft for transferring, filtering, and sorting items.

![Demo Front](https://raw.githubusercontent.com/larashores/laraspipes/main/images/demo-front.png)

This is my first mod so it has a few quirks (eg. it only works with vanilla chests). If you like it please let me know and I can consider new features!

## Supported Versions

This mod runs on the following Minecraft versionsccccv.
- [Forge 1.20.1](https://files.minecraftforge.net/net/minecraftforge/forge/index_1.20.1.html)

## Summary

This mod adds Item Extractors and Depositors to Minecraft. Item Pipes are used to connect Item Extractors and Item
Depositors. When an Extractor is connected to a Depositor through Pipes, the Extractor will attempt to remove items
from its attached chest (if any) and move them through the attached Depositor to its attached chest (if any). 

Item Depositors can have "filters" configured. See the Item Depositor section for more details. If an Extractor
is connected to a Depositor with filters, only items matching the filters will be transferred.

Item Extractors can be connected to multiple Depositors. This can be used to create an item "sorter". A single Extractor
can connect to multiple Depositors. Each Depositor can be configured with a type of items (eg. blocks, tools, plants).
This way items can be dropped of in a single chest connected to an Item Extractor, and they will be automatically be
distributed to their correct chest through each Item Depositor and its filters.

If an Item Extractor is connected to multiple Item Depositors, items will be routed to the closest Item Depositor first,
and only the second if the first chest is full. The exception to this is Item Depositors without filters. Items will
only be routed to Item Depositors without filters if there are no Item Depositors with filters that can accept the item.
This can be used to create an "extra" chest, that accepts items only if there are no configured Item Depositors with a
matching filter that can accept the item. 

![Demo Top](https://raw.githubusercontent.com/larashores/laraspipes/main/images/demo-top.png)

## Item Extractor

Item Extractors are used for removing items from chests. The large side of the extractor must face the chest to extract
items from. If an extractor is placed next to a chest, it will auto-face the chest regardless of the player direction.
Item Pipes can be connected to the Extractor from any other direction.

![Item Extractor Left](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-extractor-left.png)
![Item Extractor Right](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-extractor-right.png)

### Recipe

Item Extractors are made in a crafting table with the following recipe.
- 1 x Hopper
- 1 x Redstone Block
- 4 x Iron Ingot

![Item Extractor Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-extractor-recipe.png)


## Item Depositor

Item Depositors are used for adding items to chests. The large side of the extractor must face the chest to add
items from. If an extractor is placed next to a chest, it will auto-face the chest regardless of the player direction.
Item Pipes can be connected to the Extractor from any other direction.

![Item Depositor Left](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-left.png)
![Item Depositor Right](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-right.png)

### Menu

Right-clicking an Item Depositor will open up a menu where item "filters" can be selected. Items from the player's
inventory can be "copied" into the Item Depositor slots to set the filters. If at least one filter is set, only items
matching the filters can be added to the Item Depositor's chest. For example, only Cobblestone, Oak Planks, and
Redstone blocks can be transferred through the below Item Depositor. If no filters are selected, all types of items can
be transferred.

![Item Depositor Menu](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-menu.png)

### Recipe

Item Depositors are made in a crafting table with the following recipe.
- 1 x Hopper
- 1 x Redstone Block
- 4 x Iron Ingot

![Item Depositor Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-depositor-recipe.png)


## Item Pipe

Item Pipes are used for connecting Item Extractors and Item Depositors. They can be chained to connect
Item Depositors and Item Extractors at any distance. When connected, Item Extractors will attempt to move items to any
attached Item Depositors.

![Item Pipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-pipe.png)

### Recipe

Item Pipes are made in a crafting table with the following recipe.
- 1 x Redstone
- 2 x Iron Ingot

![Item Pipe Recipe](https://raw.githubusercontent.com/larashores/laraspipes/main/images/item-pipe-recipe.png)

## Links

- Curseforge: https://www.curseforge.com/minecraft/mc-mods/laras-pipes
- Github: https://github.com/larashores/laraspipes
- Docs: https://larashores.github.io/laraspipes