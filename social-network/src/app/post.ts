export class Post {
    constructor(
        public name?: string, 
        public description?: string,
        public visible?: string,
        public like?: number,
        public comment?: [],
        public userId?: string,
        public username?: string,
        ) { }
}