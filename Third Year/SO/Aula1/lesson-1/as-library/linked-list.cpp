#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <stdint.h>
#include <string.h>
#include <assert.h>
#include <iostream>
using namespace std;

#include "linked-list.h"

/*******************************************************/

SllNode* sllDestroy(SllNode* list)
{
    return list;
}

/*******************************************************/

void sllPrint(SllNode *list, FILE *fout)
{
    SllNode *Init = list;
    while (Init != nullptr) {
        fprintf(fout, "Nmec: %u - Name: %s \n", Init->reg.nmec, Init->reg.name);
        Init = Init -> next;
    }
}

/*******************************************************/

SllNode* sllInsert(SllNode* list, uint32_t nmec, const char *name)
{
    assert(name != NULL && name[0] != '\0');
    assert(!sllExists(list, nmec));

    SllNode *Nstudent = (SllNode*)malloc(sizeof(SllNode));

    if (Nstudent == NULL){
        return NULL;
    }

    Nstudent ->reg.name = strdup(name);
    Nstudent ->reg.nmec = nmec;
    Nstudent -> next = NULL;

    if(list == NULL){
        return Nstudent;
    }

    if( list->reg.nmec > nmec){
        Nstudent->next = list;
        return Nstudent;
    }

    SllNode *current = list;

    while (current->next != NULL && current->next->reg.nmec < nmec){
        current = current->next;
    }

    Nstudent->next = current->next;
    current->next = Nstudent;

    // verificar nmcs iguais


    return list;
}

/*******************************************************/

bool sllExists(SllNode* list, uint32_t nmec)
{

    while(list != NULL){
        if (list->reg.nmec == nmec){
            return true;
        }
        list = list->next;
    }
    return false;

}

/*******************************************************/

SllNode* sllRemove(SllNode* list, uint32_t nmec)
{
    assert(list != NULL);
    assert(sllExists(list, nmec));

    SllNode* current = list;
    SllNode* previous = NULL;

    while(current != NULL){
        if (current->reg.nmec == nmec){
            if(previous == NULL){
                previous = NULL;
                current = current->next;
                return current;
            }
            previous->next = current -> next;
        } 
        previous = current;
        current = current->next;
    }

    return list;
}

/*******************************************************/

const char *sllGetName(SllNode* list, uint32_t nmec)
{
    assert(list != NULL);
    assert(sllExists(list, nmec));

    while(list != NULL){
        if (list->reg.nmec == nmec){
            return list->reg.name;
        }
        list = list->next;
    }
    return NULL;
}

/*******************************************************/

SllNode* sllLoad(SllNode *list, FILE *fin, bool *ok)
{
    assert(fin != NULL);

    
    return list;
}

/*******************************************************/

