/**
 * Created by jin on 8/16/17.
 */


import {FilterArrayPipe} from './filterArray.pipe'

describe('FilterPipe', () => {
    let pipe: FilterArrayPipe
    const testString: string[] = []

    beforeEach(() => pipe = new FilterArrayPipe())

    it('Less then allowed', () => expect(pipe.transform(testString, testString.length + 1)).toEqual(testString))
    it('Equal to allowed', () => expect(pipe.transform(testString, testString.length)).toEqual(testString))
    it('More then allowed', () => expect(pipe.transform(testString, testString.length - 1)).toEqual(testString.slice(0, -1)))
})
