from mcresources import ResourceManager

from const import *


def generate(rm: ResourceManager):
    for weapons in bone_weapons:
        rm.item_tag('ifc:lightning_weapons', 'ifc:bone/%s/lightning' % weapons)
        rm.item_tag('ifc:fire_weapons', 'ifc:bone/%s/fire' % weapons)
        rm.item_tag('ifc:ice_weapons', 'ifc:bone/%s/ice' % weapons)

    rm.item_tag('tfc:swords', *['ifc:bone/sword/%s' % b for b in ('lightning', 'fire', 'ice')])
    rm.item_tag('tfc:javelins', *['ifc:bone/javelin/%s' % b for b in ('lightning', 'fire', 'ice')])
    rm.item_tag('tfc:knives', *['ifc:bone/knive/%s' % b for b in ('lightning', 'fire', 'ice')])
    rm.item_tag('tfc:maces', *['ifc:bone/mace/%s' % b for b in ('lightning', 'fire', 'ice')])

