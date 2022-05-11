import { timeStamp } from "console";

class Template {
    name: string;
    topic: string;
    example: string;
    
    constructor(name: string, topic: string, example: string) {
        this.name = name;
        this.topic = topic;
        this.example = example;
    }
}

export default Template;
