# THE DEFINITIONS OF OBJECTS IN THE GAME
import sys, pygame, math, numpy, random, time, copy
import text_input as ti
from pygame.locals import *
from sql import *
from Buttons import *

#global definitions
WHITE = (255,255,255)
BLUE = (0,0,255)
BLACK = (0,0,0)
GRAY = (100,100,100)
GREEN = ( 0, 255, 0)
SKYBLUE = (78, 252, 240)

#The game screen player will interaction with. Includes the game, the museum, the world, and ecology.
class GameScreen():

    def __init__(self, seed, world_dimensions, screen_dimensions, name):
        self.player = Player(name)
        self.id = seed
        self.screen_dimensions = screen_dimensions
        self.world_dimensions = world_dimensions

        pygame.init()
        self.screen = pygame.display.set_mode(screen_dimensions)
        pygame.display.set_caption('SAFARI WATSON')
        self.screen.fill(WHITE)

        self.world = SafariWorld(self.screen) #hardcoded for now
        self.museum = Museum()
        self.watson = Watson()
        
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

    def run(self):
        while True:
            events = pygame.event.get()
            for event in events:
                if event.type == QUIT:
                    pygame.quit()
                    sys.exit()
            # self.ecology_test.text1.update(events)
            # self.ecology_test.text1.draw(self.screen)
            pygame.display.update()

# -------------------------------------------------------------------------------------------------
#The drag and drop game
class SafariWorld():

    def __init__(self, screen):
        self.ecology_in_world = 0
        self.player = None

        # load the food chain outline
        self.foodChainPath = "images/FoodChain1.png"
        self.foodChain = pygame.image.load(self.foodChainPath)
        self.foodChain = pygame.transform.scale(self.foodChain, (570,300))

        self.screen = screen
        self.ecologyItems = []


        # load initial 3 ecology plants/animals
        #query organisms from the database
        #TODO: this will need to be changed to read the required IDs from the database
        x = 750
        y = 175
        for i in xrange(1, 7):
            name = get_ecology_name(i)
            print "name = ", name
            e = Ecology(name, self.screen, (x, y))
            y += 75
            self.ecologyItems.append(e)

        #add text boxes:
        ti.display_box(self.screen, "", (500,600, 300, 50), (500,600))

    def play(self):
        self.add_museum_token()
        pass
        # query the organisms from the database

        # have either autocheck or 'check' button

        #remember to display more eco plants and animals with new web when player gets the first section right. Must source our plants and animals from the db if possible. 

    def add_museum_token(self):    
        # place item in museum if it's time. should place one in museum after doing the first 3 so we have it set up for future iteraitons. 
        pass

    def draw(self, dimensions, screen):
        '''pygame.draw.rect(screen, BLUE, dimensions)

        font = pygame.font.SysFont('timesnewroman', 14)
        text = font.render('Game Part: ', True, WHITE)
        screen.blit(text, (700 // 2 , 700 // 2))'''

        screen.blit(self.foodChain,(100,100))
        pygame.display.flip()

# -------------------------------------------------------------------------------------------------
# WATSON

class Watson():

    def __init__(self):
        pass
        #make clickable

        #have an question box, an 'ask' button, and an answer section where the information from watson is displayed. 

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
        #make msuseum clickable. should open to a new page where it displays the tokens and has a back button

        #when clicked on the token, should present 3 subtopics based on player's interest

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

    def __init__(self, name, screen, dimensions): #might want to add type_of_creature
        self.name = name
        #self.type = type_of_creature
        self.location = dimensions
        #the questions that each of the items require
        self.questions = ['q1', 'q2', 'q3']
        self.answers = [0,0,0]
        
        #opacity should be either 0, 1, 2, or 3, depending on how many questions the person has answered about the specific animal/plant
        self.opacity = 0 

        self.location = False
        self.draw_self(screen, name, dimensions)

    def draw_self(self, screen, name, dimensions):
        circle = pygame.draw.circle(screen, GREEN, dimensions, 30, 0)
        font = pygame.font.SysFont('timesnewroman', 12)
        text = font.render( name, True, BLACK)

        #centre the text in the circle:
        textpos = text.get_rect()
        textpos.centerx = circle.centerx
        textpos.centery = circle.centery

        screen.blit(text, textpos)
        #screen.blit(text, (int(dimensions[0]) - 10, int(dimensions[1]) -7))

    def update_opacity(self):
        self.opacity = sum(self.answers)
        return self.opacity

    def update_location(self, new_dimensions): #MUST BE A TUPLE
        self.location = new_dimensions

    def get_location(self):
        return self.location

    def update_interest(self):
        #call to user's db file to update interest for this ecology item. 
        answered_questions = self.update_opacity()

    def make_clickable(self):
        if self.location == True:
            #make clickable
            pass

    def ask_questions(self):
        if pygame.event.get().type == MOUSEBUTTONUP:
            self.draw_question_box(screen)

    def get_questions(self):
        #call db
        for x in range(0,3):
            self.questions.append(db.q1)

    def draw_question_box(self, screen):
        pygame.draw.rect(screen, GRAY, (200,30,500,160)) #location, size
        font = pygame.font.SysFont('timesnewroman', 14)
        text = font.render(str(self.name) +' questions: ', True, BLACK)
        screen.blit(text, (210 , 40))
        question_dimensions = [((210, 70, 450, 30), (220,80)), ((210, 110, 450, 30), (220, 120)), ((210, 150, 450, 30), (220, 160))]
        ti.ask(screen, self.questions, question_dimensions) #location, size (left/right, up/down)

        # txtbx = et.Input(maxlength=45, color=(255,0,0), prompt='q1: ')
        # return txtbx






