from enum import Enum
from itertools import repeat
from typing import Union

from mcresources import ResourceManager, RecipeContext, utils
from mcresources.type_definitions import Json, ResourceIdentifier

from const import *


def generate(rm: ResourceManager):

    for weapons in bone_weapons:
        blood_recipe(rm, '%s/lightning' % weapons, 'ifc:bone/%s/dragon_bone' % weapons, 'ifc:bone/%s/lightning' % weapons, bloodtype='lightning')
        blood_recipe(rm, '%s/fire' % weapons, 'ifc:bone/%s/dragon_bone' % weapons, 'ifc:bone/%s/fire' % weapons, bloodtype='fire')
        blood_recipe(rm, '%s/ice' % weapons, 'ifc:bone/%s/dragon_bone' % weapons, 'ifc:bone/%s/ice' % weapons, bloodtype='ice')



def blood_recipe(rm: ResourceManager, name: ResourceIdentifier, item: str, result: str, count: int = 1, bloodtype=None) -> RecipeContext:
    return rm.recipe(('blood_alchemy',name),'ifc:blood_alchemy',{
        'ingredient': utils.ingredient(item),
        'result': utils.item_stack((count, result)),
        'blood_type': bloodtype
    })