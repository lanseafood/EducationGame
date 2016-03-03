# THE DEFINITIONS OF OBJECTS IN THE GAME
import sys, pygame, math, numpy, random, time, copy
from pygame.locals import * 

#global definitions
WHITE = (255,255,255)
BLUE = (0,0,255)
BLACK = (0,0,0)

#The game screen player will interaction with. Includes the game, the museum, the world, and ecology.
class GameScreen():

    def __init__(self, seed, world_dimensions, screen_dimensions, name):
        self.player = Player(name)
        self.id = seed
        self.screen_dimensions = screen_dimensions
        self.world_dimensions = world_dimensions
        self.world = SafariWorld() #hardcoded for now
        self.museum = Museum()
        self.watson = Watson()

        pygame.init()
        self.screen = pygame.display.set_mode(screen_dimensions)
        pygame.display.set_caption('SAFARI WATSON')
        self.screen.fill(WHITE)
        
        self.world.draw(self.world_dimensions, self.screen)
        self.museum.draw(self.screen)
        self.watson.draw((150,720,600,100), self.screen)

        pygame.draw.rect(self.screen, BLUE, (30,720,100,100)) #showing player
        font = pygame.font.SysFont('timesnewroman', 14)
        text = font.render('Player: ', True, BLACK)
        self.screen.blit(text, (30 , 720))
        
        self.draw_score(self.screen)


    def draw_score(self, screen):
        font = pygame.font.SysFont('timesnewroman', 14)

        text = font.render('Score: ', True, (0,128,0))
        self.screen.blit(text, (100 // 2 , 100 // 2))



# -------------------------------------------------------------------------------------------------
#The drag and drop game
class SafariWorld():

    def __init__(self):
        self.ecology_in_world = 0
        self.player = None

    def draw(self, dimensions, screen):
        pygame.draw.rect(screen, BLUE, dimensions)

        font = pygame.font.SysFont('timesnewroman', 14)
        text = font.render('Game Part: ', True, WHITE)
        screen.blit(text, (700 // 2 , 700 // 2))

# -------------------------------------------------------------------------------------------------
# WATSON

class Watson():

    def __init__(self):
        pass

    def draw(self, dimensions, screen):
        pygame.draw.rect(screen, BLUE, dimensions)

        font = pygame.font.SysFont('timesnewroman', 14)
        text = font.render('Ask Watson: ', True, WHITE)
        screen.blit(text, (150,800))

# -------------------------------------------------------------------------------------------------
# THE STUDENTS PLAYING THE GAME
class Player():

    def __init__(self, name):
        self.name = name
        self.status = 'noob'
        self.score = 0

    def update_status(self):
        if self.score > 100:
            self.status = 'cub'
        elif self.score > 300:
            self.score = 'lion tamer'
        elif self.score > 500:
            self.status = 'lion king'
        else:
            self.score = 'godlike'

# -------------------------------------------------------------------------------------------------
# THE MUSEUM FOR EACH PLAYER

class Museum():

    def __init__(self):
        self.tokens = [] #the tokens in the museum

    def add_token(self, token):
        self.tokens.add(token)

    def draw(self, screen):
        pygame.draw.rect(screen, BLUE, (750,30,100,100))
        font = pygame.font.SysFont('timesnewroman', 14)
        text = font.render('Museum: ', True, BLACK)
        screen.blit(text, (750 , 30))
# -------------------------------------------------------------------------------------------------
# THE REWARDS PEOPLE GET TO PUT IN THE MUSEUM
class Token():

    def __init__(self, name):
        self.name = name

# -------------------------------------------------------------------------------------------------
#THE ANIMALS AND PLANTS THAT WE WANT IN THE FOOD CHAIN

class Ecology():

    def __init__(self, name, type_of_creature):
        self.name = name
        self.type = type_of_creature

        #the questions that each of the items require
        self.q1 = None
        self.q2 = None
        self.q3 = None
        
        #opacity should be either 0, 1, 2, or 3, depending on how many questions the person has answered about the specific animal/plant
        self.opacity = 0 


    def update_opacity(self):
        pass




