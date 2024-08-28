/**
 * CreateBoard is defined as
 * `<e-create-board>`
 *
 * Imperatively create application
 * @example
 * let app = new CreateBoard();
 *
 * Declaratively create application
 * @example
 * <e-create-board></e-create-board>
 *
 * @extends {App}
 */
import { definition } from '@eui/component';
import { App, html } from '@eui/app';
import style from './createBoard.css';

@definition('e-create-board', {
  style,
  props: {
    response: { attribute: false },
    teammembers: { attribute: false, type: "array", default: [] },
  },
})
export default class CreateBoard extends App {
  /**
   * Uncomment this block to add initialization code
   * constructor() {
   *   super();
   *   // initialize
   * }
   */

  constructor() {
    super();

    fetch('/api/team/' + window.localStorage.teamid)
      .then(function (response) {
        console.log(response)
        return response.json()
      }).then(function (json) {
        this.teamDetails = json.data
        this.teammembers = json.data.users;
      }.bind(this)).catch(function (ex) {
        console.log('parsing failed', ex)
      })

      this.addBoard = this.addBoard.bind(this)

  }

  /**
  * Render the <e-create-board> app. This function is called each time a
  * prop changes.
  */
 addBoard(event) {
  fetch('/api/addBoard', {
    headers: {
        'Content-type': 'application/json'
    },
    method: "POST",
    body: JSON.stringify({ "boardDesc":this.boadDesc,
    "teamId":this.teamDetails.teamId})
}).then((json) => {
    console.log(json);
    alert("Board added successfully");
}).catch((ex) => {
    console.log('parsing failed', ex);

    // apiHelper.post('/api/createTeam', {
    //     teamName: this.teamName
    // }).then(json => console.log(json));
    //
});
}
  render() {
    const { EUI } = window;
    const usernames = this.teammembers.map(item => { return html`<p>${item.username}</p>` })
    return html`
  
  <div class="flex-display">
    <eui-layout-v0-card class="team-card float-left" card-title="Add board">
      <div slot="content">
      <div class="flex-display">
        <p class="float-left">Board Description: </p>
        <eui-base-v0-text-field @input="${event => this.boadDesc = event.detail.value}" style="padding-left:5px;" class="float-right"></eui-base-v0-text-field>
        </div>
        <div class="float-right">
        <eui-base-v0-button class="button-add-card" @click="${this.addBoard}">
            Add Board
        </eui-base-v0-button>
        </div>
      </div>
      
    </eui-layout-v0-card>
    
    <eui-layout-v0-card class="team-card float-right card-white-color" card-title="Team members">
      <div slot="content">
      
        <div> ${usernames} </div>
      </div>
  </eui-layout-v0-card></div>`;
  }
}

/**
 * Register the component as e-create-board.
 * Registration can be done at a later time and with a different name
 * Uncomment the below line to register the App if used outside the container
 */
CreateBoard.register();
