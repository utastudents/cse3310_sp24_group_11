const { test, expect } = require('@jest/globals');

const { generateGrid, setupGridEvents } = require('game.js');

const testGrid = document.createElement('div');
testGrid.id = 'wordSearchGrid';
document.getElementById = jest.fn().mockReturnValue(testGrid);

test('DOMContentLoaded event calls generateGrid and setupGridEvents', () => {
    document.dispatchEvent(new Event('DOMContentLoaded'));

    expect(generateGrid).toHaveBeenCalled();
    expect(setupGridEvents).toHaveBeenCalled();
});
