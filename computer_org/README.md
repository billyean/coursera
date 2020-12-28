#  Computer Organization

## MARS download

[MARS official download link](http://courses.missouristate.edu/KenVollmar/Mars/index.htm)

## implementation

### 用系统功能调用实现简单输入输出

[利用系统功能调用从键盘输入，转换后在屏幕上显示](mars45/mips1.asm)：
具体要求如下
* 如果输入的是字母（A\~Z，区分大小写）或数字（0\~9），则将其转换成对应的英文单词后在屏幕上显示，对应关系见下表
* 若输入的不是字母或数字，则在屏幕上输出字符“*”，
* 每输入一个字符，即时转换并在屏幕上显示，
* 支持反复输入，直到按“?”键结束程序。

|| ||     ||  ||         ||   ||     ||  ||      ||  ||        ||
|--|-------|---|----------|---|-------|---|-------|---|---------|
|A | Alpha | N | November | 1 | First | a | alpha | n | november|
|B | Bravo | O | Oscar    | 2 | Second| b	| bravo	| o	| oscar   |
|C | China | P | Paper    | 3 | Third | c	| china	| p	| paper   |
|D | Delta | Q | Quebec   | 4 | Fourth| d	| delta	| q	| quebec  |
|E | Echo  | R | Research | 5 | Fifth | e	| echo	| r	| research|
|F | Foxtrot|S | Sierra   | 6 | Sixth | f	| foxtrot|s | sierra  |
|G | Golf  | T | Tango    | 7 | Seventh|g	| golf	| t	| tango   |
|H | Hotel | U | Uniform  | 8 | Eighth| h	| hotel	| u	| uniform |
|I | India | V | Victor   | 9 | Ninth	| i	| india	| v	| victor  |
|J | Juliet| W | Whisky   | 0 | zero	| j	| juliet| w	| whisky  |
|K | Kilo  | X | X-ray    |  	| 	    | k	| kilo	| x	| x-ray   |
|L | Lima  | Y | Yankee   |  	| 	    | l	| lima	| y	| yankee  |
|M | Mary  | Z | Zulu     | 	| 	    | m	| mary	| z	| zulu    |

### 字符串查找比较

[字符串查找比较](mars45/mips2.asm)

 利用系统功能调用从键盘输入一个字符串，然后输入单个字符，查找该字符串中是否有该字符（区分大小写）。具体要求如下：

* 如果找到，则在屏幕上显示：
```
Success! Location: X
```
其中，X为该字符在字符串中第一次出现的位置
* 如果没找到，则在屏幕上显示：
```
Fail!
```
* 输入一个字符串后，可以反复输入希望查询的字符，直到按“?”键结束程序
* 每个输入字符独占一行，输出查找结果独占一行，位置编码从1开始。

提示：为避免歧义，字符串内不包含"?"符号
格式示例如下：
```
abcdefgh
a
Success! Location: 1
x
Fail!
```

## License

    Copyright [2017] [Haibo Yan]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
