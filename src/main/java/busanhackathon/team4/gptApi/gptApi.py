import sys
import os
import openai
from openai.error import OpenAIError

sys.stdout.reconfigure(encoding='utf-8')

message = """
지금부터 너는 요리 레시피를 추천해줄거야 단 조건이 있어.

첫줄에는 내가 가지고 있는 재료들이 입력돼 (이 재료들을 활용해서 레시피를 추천해줘)
두번째 줄에는 내가 못먹는 재료들이 입력돼 (이 재료들은 반드시 제외하고 요리 레시피를 추천해줘)
세번째 줄에는 요리의 난이도가 입력돼 (위의 재료들을 통해 만들 수 있는 레시피중 내가 입력한 난이도에 알맞게 추천해줘)

가능하면 최대한 다른 재료를 많이 사용안하면 좋겠어
하지만 누구나 있을 법한 재료는 넣어도 돼
그리고 내가 첫번째 줄에 적은 재료를 사용하지 않아도 돼

출력 형식은 아래와 같게 해줘
### 음식 이름:
- 음식이름
### 재료:
- 재료1
- 재료2
- 재료3
...
### 레시피:
1. 1단계
2. 2단계
3. 3단계
...
재료와 레시피는 최대한 길고 자세하게 적어줘
"""

messages=[{"role": "system", "content": message }]

def ask(q):
    q = {"role" : "user" , "content" : q}
    messages.append(q)

    response = openai.ChatCompletion.create(model="gpt-3.5-turbo", messages = messages)

    res = response.to_dict_recursive()
    bot_text  = response['choices'][0]['message']['content']
    bot_input = {"role": "assistant", "content": bot_text }

    messages.append(bot_input)

    return bot_text

# Load your API key from an environment variable or secret management service
openai.api_key = <api 키>

query = sys.argv[1]

try:
    ans = ask(query)
    print(ans)
except OpenAIError as e:
    print(e._message)
sys.stdout.flush()
