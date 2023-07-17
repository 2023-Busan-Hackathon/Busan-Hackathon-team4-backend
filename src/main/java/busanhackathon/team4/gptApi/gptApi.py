import sys
import os
import openai
from openai.error import OpenAIError

sys.stdout.reconfigure(encoding='utf-8')

message = """
첫줄에는 요리할 재료들이 들어와
두번째줄에는 못먹는 재료가 들어와서 이 재료는 제외하고 요리 레시피를 추천해줘야해
세번째 줄에는 요리 난이도가 나와

가능하면 최대한 다른 재료를 많이 사용안하면 좋겠어 하지만 누구나 있을 법한 재료는 넣어도 돼
이 세줄의 정보를 통해 요리할 음식을 추천해주며, 추가적으로 재료들과 레시피를 자세히 적어줘
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
openai.api_key = <API KEY>

query = sys.argv[1]

try:
    ans = ask(query)
    print(ans)
except OpenAIError as e:
    print(e._message)
sys.stdout.flush()