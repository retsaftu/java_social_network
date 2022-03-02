export class User {
    constructor(
        public username?: string,
        public name?: string,
        public email?: string,
        public password?: string,
        public friends?: [],
    ) { }
}
export class UserComment {
    constructor(
        public content?: string,
        public name?: string,
        public userId?: string,
    ) { }
}