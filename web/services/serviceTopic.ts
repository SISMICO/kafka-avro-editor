export default function getTopics(topic: string) {
    return fetch(`http://localhost:8080/json/${topic}`, {
        method: 'get'
    })
        .then(res => res.json())
        .catch(console.log);
}
