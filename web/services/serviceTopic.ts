async function sendEvent(topic: string, message: string) {
    try {
        return await fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/send/${topic}`, {
            method: 'post',
            body: message
        }).then(res => res.text())
        .catch(
            ex => {
                console.log(ex);
            }
        )
    } catch (ex) {
        console.log(ex);
    }
}

export { sendEvent }
