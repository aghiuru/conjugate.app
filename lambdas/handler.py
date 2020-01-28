import json
import random
from handler import *

cat_verbs = json.load(open('dicts/cat.json'))
es_verbs = json.load(open('dicts/es.json'))
it_verbs = json.load(open('dicts/it.json'))
fr_verbs = json.load(open('dicts/fr.json'))

dict_map = {
    "cat": cat_verbs,
    "es": es_verbs,
    "it": it_verbs,
    "fr": fr_verbs,
}

def random_conjugation(lang):
    dictionary = dict_map[lang]
    conjugations = dictionary['conjugations']

    random_index = random.randint(0, len(conjugations))
    conj = conjugations[random_index][0]

    pronouns = dictionary['pronouns']
    person = conj['person']
    pronoun = pronouns[str(person)] if person is not None else None

    verbs = dictionary['verbs']
    verb = verbs[conj['verb']]

    return {
        'verb': conj['verb'],
        'person': conj['person'],
        'regular?': conj['regular?'],
        'conjugation': conj['conjugation'],
        'tense': conj['tense'],
        'tags': verb['tags'],
        'translations': verb['translations'],
        'pronoun': pronoun,
    }

def conjugation(event, context):
    qsp = event['queryStringParameters']
    lang = qsp['lang']
    conj = random_conjugation(lang)
    response = {
        "statusCode": 200,
        "headers": {
            'Access-Control-Allow-Origin': '*',
            'Access-Control-Allow-Credentials': True,
        },
        "body": json.dumps(conj)
    }

    return response

    # Use this code if you don't use the http event with the LAMBDA-PROXY
    # integration
    """
    return {
        "message": "Go Serverless v1.0! Your function executed successfully!",
        "event": event
    }
    """
