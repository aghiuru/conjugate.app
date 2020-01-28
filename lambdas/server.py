from aiohttp import web
import aiohttp_cors
import json
import handler

async def conjugation(request):
    event = { "queryStringParameters": request.query }
    handler_response = handler.conjugation(event, {})
    response = json.loads(handler_response['body'])
    return web.Response(
        text=json.dumps(response),
        content_type='application/json'
    )

app = web.Application()
app.add_routes([web.get('/conjugation', conjugation)])

cors = aiohttp_cors.setup(app, defaults={
    "*": aiohttp_cors.ResourceOptions(
        allow_credentials=True,
        expose_headers="*",
        allow_headers="*")})

for route in list(app.router.routes()):
    cors.add(route)

web.run_app(app)
