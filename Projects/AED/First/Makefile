# make              # to compile files and create the executables
# make pgm          # to download example images to the pgm/ dir
# make setup        # to setup the test files in test/ dir
# make tests        # to run basic tests
# make clean        # to cleanup object files and executables
# make cleanobj     # to cleanup object files only

CFLAGS = -Wall -O2 -g

PROGS = imageTool imageTest

TESTS = test1 test2 test3 test4 test5 test6 test7 test8 test9

# Default rule: make all programs
all: $(PROGS)

imageTest: imageTest.o image8bit.o instrumentation.o error.o

imageTest.o: image8bit.h instrumentation.h

imageTool: imageTool.o image8bit.o instrumentation.o error.o

imageTool.o: image8bit.h instrumentation.h

# Rule to make any .o file dependent upon corresponding .h file
%.o: %.h

pgm:
	wget -O- https://sweet.ua.pt/jmr/aed/pgm.tgz | tar xzf -

.PHONY: setup
setup: test/

test/:
	wget -O- https://sweet.ua.pt/jmr/aed/test.tgz | tar xzf -
	@#mkdir -p $@
	@#curl -s -o test/aed-trab1-test.zip https://sweet.ua.pt/mario.antunes/aed/test/aed-trab1-test.zip
	@#unzip -q -o test/aed-trab1-test.zip -d test/

test1: $(PROGS) setup
	./imageTool test/original.pgm neg save neg.pgm
	cmp neg.pgm test/neg.pgm

test2: $(PROGS) setup
	./imageTool test/original.pgm thr 128 save thr.pgm
	cmp thr.pgm test/thr.pgm

test3: $(PROGS) setup
	./imageTool test/original.pgm bri .33 save bri.pgm
	cmp bri.pgm test/bri.pgm

test4: $(PROGS) setup
	./imageTool test/original.pgm rotate save rotate.pgm
	cmp rotate.pgm test/rotate.pgm

test5: $(PROGS) setup
	./imageTool test/original.pgm mirror save mirror.pgm
	cmp mirror.pgm test/mirror.pgm

test6: $(PROGS) setup
	./imageTool test/original.pgm crop 100,100,100,100 save crop.pgm
	cmp crop.pgm test/crop.pgm

test7: $(PROGS) setup
	./imageTool test/small.pgm test/original.pgm paste 100,100 save paste.pgm
	cmp paste.pgm test/paste.pgm

test8: $(PROGS) setup
	./imageTool test/small.pgm test/original.pgm blend 100,100,.33 save blend.pgm
	cmp blend.pgm test/blend.pgm

test9: $(PROGS) setup
	./imageTool test/original.pgm blur 7,7 save blur.pgm
	cmp blur.pgm test/blur.pgm

testlocate1: $(PROGS) setup
	./imageTool test/crop.pgm test/original.pgm locate

testlocate2: $(PROGS) setup # pequena na media
	./imageTool pgm/small/art3_222x217.pgm pgm/medium/mandrill_512x512.pgm paste 50,222 save PasteTest.pgm
	./imageTool pgm/small/art3_222x217.pgm PasteTest.pgm locate

testlocate3.0: $(PROGS) setup # pequena na grande (fim)
	./imageTool pgm/small/art4_300x300.pgm pgm/large/airfield-05_1600x1200.pgm paste 1300,900 save PasteTest.pgm
	./imageTool pgm/small/art4_300x300.pgm PasteTest.pgm locate

testlocate3.0.1: $(PROGS) setup # pequena na grande (meio)
	./imageTool pgm/small/art4_300x300.pgm pgm/large/airfield-05_1600x1200.pgm paste 0,0 save PasteTest.pgm
	./imageTool pgm/small/art4_300x300.pgm PasteTest.pgm locate

testlocate3.0.2: $(PROGS) setup # pequena na grande (inicio)
	./imageTool pgm/small/art4_300x300.pgm pgm/large/airfield-05_1600x1200.pgm paste 650,450 save PasteTest.pgm
	./imageTool pgm/small/art4_300x300.pgm PasteTest.pgm locate

testlocate3.0.3: $(PROGS) setup # pequena na grande (meio e inicio)
	./imageTool pgm/small/art4_300x300.pgm pgm/large/airfield-05_1600x1200.pgm paste 325,225 save PasteTest.pgm
	./imageTool pgm/small/art4_300x300.pgm PasteTest.pgm locate

testlocate3.0.4: $(PROGS) setup # pequena na grande (meio e fim)
	./imageTool pgm/small/art4_300x300.pgm pgm/large/airfield-05_1600x1200.pgm paste 975,675 save PasteTest.pgm
	./imageTool pgm/small/art4_300x300.pgm PasteTest.pgm locate

testlocate3.1.0: $(PROGS) setup # com a mesma grande e diferente pequenas 
	./imageTool pgm/small/art4_300x300.pgm pgm/large/airfield-05_1600x1200.pgm paste 975,675 save PasteTest.pgm
	./imageTool pgm/small/art4_300x300.pgm PasteTest.pgm locate

testlocate3.1.1: $(PROGS) setup # com a mesma grande e diferente pequenas 
	./imageTool pgm/small/art3_222x217.pgm pgm/large/airfield-05_1600x1200.pgm paste 975,675 save PasteTest.pgm
	./imageTool pgm/small/art3_222x217.pgm PasteTest.pgm locate

testlocate3.1.2: $(PROGS) setup # com a mesma grande e diferente pequenas 
	./imageTool pgm/small/bird_256x256.pgm pgm/large/airfield-05_1600x1200.pgm paste 975,675 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.2.0: $(PROGS) setup # com a mesma pequena e diferente grandes 
	./imageTool pgm/small/bird_256x256.pgm pgm/large/ireland-06-1200x1600.pgm paste 300,300 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.2.1: $(PROGS) setup # com a mesma pequena e diferente grandes 
	./imageTool pgm/small/bird_256x256.pgm pgm/large/einstein_940x940.pgm paste 300,300 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.2.2: $(PROGS) setup # com a mesma pequena e diferente grandes 
	./imageTool pgm/small/bird_256x256.pgm pgm/large/airfield-05_1600x1200.pgm paste 300,300 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.2.3: $(PROGS) setup # com a mesma pequena e diferente grandes 
	./imageTool pgm/small/bird_256x256.pgm pgm/large/ireland_03_1600x1200.pgm paste 300,300 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.3.1: $(PROGS) setup # com a mesma pequena e diferente medias 
	./imageTool pgm/small/bird_256x256.pgm pgm/medium/airfield-05_640x480.pgm paste 100,100 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.3.2: $(PROGS) setup # com a mesma pequena e diferente medias 
	./imageTool pgm/small/bird_256x256.pgm pgm/medium/ireland-03_640x480.pgm paste 100,100 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.3.3: $(PROGS) setup # com a mesma pequena e diferente medias 
	./imageTool pgm/small/bird_256x256.pgm pgm/medium/mandrill_512x512.pgm paste 100,100 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.3.4: $(PROGS) setup # com a mesma pequena e diferente medias 
	./imageTool pgm/small/bird_256x256.pgm pgm/medium/tac-pulmao_512x512.pgm paste 100,100 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate

testlocate3.3.5: $(PROGS) setup # com a mesma pequena e diferente medias 
	./imageTool pgm/small/bird_256x256.pgm pgm/medium/tools_2_765x460.pgm paste 100,100 save PasteTest.pgm
	./imageTool pgm/small/bird_256x256.pgm PasteTest.pgm locate


testlocate5: $(PROGS) setup # media na grande
	./imageTool pgm/medium/tools_2_765x460.pgm pgm/large/ireland_03_1600x1200.pgm paste 50,222 save PasteTest.pgm
	./imageTool pgm/medium/tools_2_765x460.pgm PasteTest.pgm locate

testblur1: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 2,9 save BlurTest.pgm

	# fazer com blurs diferentes na mesma imagem

testblur2.0: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 1,1 save BlurTest.pgm

testblur2.1: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 2,2 save BlurTest.pgm

testblur2.2: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 3,3 save BlurTest.pgm

testblur2.3: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 4,4 save BlurTest.pgm

testblur2.4: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 5,5 save BlurTest.pgm

testblur2.5: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 6,6 save BlurTest.pgm

testblur2.6: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 7,7 save BlurTest.pgm

testblur2.7: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 8,8 save BlurTest.pgm

testblur2.8: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 9,9 save BlurTest.pgm

testblur2.9: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 10,10 save BlurTest.pgm

testblur2.10: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 11,11 save BlurTest.pgm

testblur2.11: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 13,13 save BlurTest.pgm

testblur2.12: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 15,15 save BlurTest.pgm

testblur2.13: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 17,17 save BlurTest.pgm

testblur2.14: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 19,19 save BlurTest.pgm

	# imagens de diferentes tamanhos

testblur3.1: $(PROGS) setup
	./imageTool pgm/large/airfield-05_1600x1200.pgm blur 9,9 save BlurTest.pgm

testblur3.2: $(PROGS) setup
	./imageTool pgm/large/einstein_940x940.pgm blur 9,9 save BlurTest.pgm

testblur3.3: $(PROGS) setup
	./imageTool pgm/large/ireland-06-1200x1600.pgm blur 9,9 save BlurTest.pgm

testblur3.4: $(PROGS) setup
	./imageTool pgm/medium/airfield-05_640x480.pgm blur 9,9 save BlurTest.pgm

testblur3.5: $(PROGS) setup
	./imageTool pgm/medium/mandrill_512x512.pgm blur 9,9 save BlurTest.pgm

testblur3.6: $(PROGS) setup
	./imageTool pgm/medium/tools_2_765x460.pgm blur 9,9 save BlurTest.pgm

testblur3.7: $(PROGS) setup
	./imageTool pgm/small/art3_222x217.pgm blur 9,9 save BlurTest.pgm

testblur3.8: $(PROGS) setup
	./imageTool pgm/small/art4_300x300.pgm blur 9,9 save BlurTest.pgm

.PHONY: tests
tests: $(TESTS)

# Make uses builtin rule to create .o from .c files.

cleanobj:
	rm -f *.o

clean: cleanobj
	rm -f $(PROGS)

