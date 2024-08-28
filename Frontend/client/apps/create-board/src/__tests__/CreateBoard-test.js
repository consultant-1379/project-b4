/**
 * Integration tests for <e-create-board>
 */
import { expect } from 'chai';
import CreateBoard from '../CreateBoard';
import {
  inShadow,
  injectHTMLElement,
} from '../../../../../test/utils';

describe('CreateBoard Application Tests', () => {
    let container;
    let inject;
    before(() => {
      container = document.body.appendChild(document.createElement('div'));
      inject = injectHTMLElement.bind(null, container);
      window.EUI = undefined; // stub out the locale
      CreateBoard.register();
    });

    after(() => {
      document.body.removeChild(container);
    });

    describe('Basic application setup', () => {
      it('should create a new <e-create-board>', async () => {
        const appUnderTest = await inject('<e-create-board></e-create-board>');
        // check shadow DOM
        const headingTag = inShadow(appUnderTest, 'h1');
        expect(headingTag.textContent, '"Your app markup goes here" was not found').to.equal('Your app markup goes here');
      });
    });
});
