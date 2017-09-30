/**
 * Created by jin on 8/16/17.
 */


import {FindKeyPipe} from './findKey.pipe'

describe('FindKeyPipe', () => {
    let pipe: FindKeyPipe
    const testString: string = 'test'

    beforeEach(() => pipe = new FindKeyPipe())

    it('Less then allowed', () => expect(pipe.transform(testString, testString.length + 1)).toEqual(testString))
    it('Equal to allowed', () => expect(pipe.transform(testString, testString.length)).toEqual(testString))
    it('More then allowed', () => expect(pipe.transform(testString, testString.length - 1)).toEqual(testString.slice(0, -1)))
})
