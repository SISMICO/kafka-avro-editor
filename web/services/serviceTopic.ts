function getTopics(topic: string) {
    return fetch(`http://localhost:8080/json/${topic}`, {
        method: 'get'
    })
        .then(res => res.json())
        .catch(console.log);
}

function sendEvent(topic: string, message: string) {
    return fetch(`http://localhost:8080/send/${topic}`, {
        method: 'post',
        body: message
    })
        .then(res => res.text())
        .catch(console.log);
}

export { getTopics, sendEvent }
