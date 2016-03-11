import pygame, sys, core
from core import *
from pygame.locals import *


world = GameScreen(0, (200,200, 500, 500), (900,900), 'bob')
# pygame.init()
# DISPLAYSURF = pygame.display.set_mode((400,400))
# pygame.display.set_caption('SAFARI WATSON')

world.run()
# while True:
# 		for event in pygame.event.get():
# 			if event.type == QUIT:
# 				pygame.quit()
# 				sys.exit()
# 		pygame.display.update()