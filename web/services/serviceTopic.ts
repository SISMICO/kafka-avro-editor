function getTopics(topic: string) {
    return fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/json/${topic}`, {
        method: 'get'
    })  
        .then(res => res.json())
        .catch(console.log);
}

function sendEvent(topic: string, message: string) {
    return fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/send/${topic}`, {
        method: 'post',
        body: message
    })
        .then(res => res.text())
        .catch(console.log);
}

export { getTopics, sendEvent }
