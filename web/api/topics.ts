
export async function getTopics(filter: string) {
    const response = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_HOST}/topics`, {
        method: 'GET'
    })
    .then(
        data => data.json()
    ).then(
        data => data?.filter( (item: string) => item.indexOf(filter) >= 0 )
    );
    
    return response;
}
