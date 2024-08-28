/**
 * Integration tests for <e-story-table>
 */
import { expect } from 'chai';
import '../StoryTable';
import {
  inShadow,
  injectHTMLElement,
  simulateEvent,
  nextTick
} from '../../../../../test/utils';

describe('StoryTable Component Tests', () => {
    let container;
    let inject;
    before(() => {
      container = document.body.appendChild(document.createElement('div'));
      inject = injectHTMLElement.bind(null, container);
    });

    after(() => {
      document.body.removeChild(container);
    });

    describe('Basic component setup', () => {
      it('should create a new <e-story-table>', async () => {
        const customElement = await inject('<e-story-table></e-story-table>');
        // check shadow DOM
        const headingTag = inShadow(customElement, 'h1');
        expect(headingTag.textContent, '"Your component markup goes here" was not found').to.equal('Your component markup goes here');
      });
    });
});
