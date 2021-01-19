'''
国际摩尔斯密码定义一种标准编码方式，将每个字母对应于一个由一系列点和短线组成的字符串， 比如: "a" 对应 ".-", "b" 对应 "-...", "c" 对应 "-.-.", 等等。

为了方便，所有26个英文字母对应摩尔斯密码表如下：

[".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
给定一个单词列表，每个单词可以写成每个字母对应摩尔斯密码的组合。例如，"cab" 可以写成 "-.-..--..."，(即 "-.-." + "-..." + ".-"字符串的结合)。我们将这样一个连接过程称作单词翻译。

返回我们可以获得所有词不同单词翻译的数量。

例如:
输入: words = ["gin", "zen", "gig", "msg"]
输出: 2
解释: 
各单词翻译如下:
"gin" -> "--...-."
"zen" -> "--...-."
"gig" -> "--...--."
"msg" -> "--...--."

共有 2 种不同翻译, "--...-." 和 "--...--.".
 

注意:

单词列表words 的长度不会超过 100。
每个单词 words[i]的长度范围为 [1, 12]。
每个单词 words[i]只包含小写字母。
'''

arr = [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]



class Solution:
    def uniqueMorseRepresentations(self, words):
        hashSet = set();
        for i in words:
            value = ""
            for x in i:
                key = arr[ord(x) - ord('a')]
                value += key
            hashSet.add(value)
        return len(hashSet)


r = Solution().uniqueMorseRepresentations(["gin", "zen", "gig", "msg"])
print(r)
        





'''c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

char * morse[] = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
        "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.",
        "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.." };

struct Node {
    char *value;
    struct Node * next;
};

struct List {
    int size;
    struct Node * head;
};

bool contains(struct List * list, char *value) {
    struct Node * node = list->head;
    while (node != NULL) {
        if (strcmp(node->value, value) == 0) {
            return true;
        }
        node = node->next;
    }
    return false;
}

void add(struct List * list, char *value) {
    if (list->head == NULL) {
        list->head = (struct Node *) calloc(sizeof(struct Node), 1);
        list->head->value = value;
        list->head->next = NULL;
        list->size++;
    } else {
        if (!contains(list, value)) {
            struct Node * node = list->head;
            while (node->next != NULL) {
                node = node->next;
            }
            node->next = (struct Node *) calloc(sizeof(struct Node), 1);
            node->next->value = value;
            node->next->next = NULL;
            list->size++;
        }
    }
}

void clear(struct List * list) {
    if (list != NULL) {
        if (list->head != NULL) {
            struct Node * node = list->head;
            while (node != NULL) {
                struct Node * freeNode = node;
                node = node->next;
                free(freeNode->value);
                free(freeNode);
            }
        }
    }
}

int uniqueMorseRepresentations(char** words, int wordsSize) {
    struct List list = { 0, NULL };
    for (int i = 0; i < wordsSize; i++) {
        char *word = words[i];
        char * buf = (char *) calloc(sizeof(char), (4 * 12) + 1);
        for (int x = 0; x < strlen(word); x++) {
            char ch = word[x];
            char * key = morse[ch - 'a'];
            strcat(buf,key);
        }
        add(&list, buf);
    }
    int count = list.size;
    clear(&list);
    return count;
}
int main(int argc, char **argv) {
    char * arr[] = { "gin", "zen", "gig", "msg" };
    int result = uniqueMorseRepresentations(arr, 4);
    printf("%d\n", result);
    return EXIT_SUCCESS;
}


'''









