/**
 * App1 is defined as
 * `<e-app-1>`
 *
 * Imperatively create application
 * @example
 * let app = new App1();
 *
 * Declaratively create application
 * @example
 * <e-app-1></e-app-1>
 *
 * @extends {App}
 */
import {definition} from '@eui/component';
import {App, html} from '@eui/app';
import style from './app1.css';
import '../../../components/story-table/src/StoryTable';


@definition('e-app-1', {
    style,
    props: {
        response: {attribute: false},
        body: {
            attribute: false,
            type: 'string',
        },
        displayCreateTeamFlyout: {
            attribute: false,
            type: 'boolean',
            default: false,
        },
        displayEditTeamFlyout: {
            attribute: false,
            type: 'boolean',
            default: false,
        },
        userList: {
            attribute: false,
            type: 'array',
            default: [],
        },
        teamName: {
            attribute: false,
            type: 'string',
        },
        boardDetails:{
            attribute: false,
            type: 'array',
            default: [],
        },
        currentboardid: {attribute: true, type:"string"}}
   
})
export default class App1 extends App {
//   Uncomment this block to add initialization code
    constructor() {
        super();
        // // initialize
        //
        // apiHelper.get('/hello')
        //     .then(json => console.log(json));

        // apiHelper.post('/hello', 'ACRS')
        //     .then(json => console.log(json));
        //
        // apiHelper.put('/hello', 'ACRS')
        //     .then(json => console.log(json));
        //
        // apiHelper.delete('/hello', 'ACRS')
        //     .then(json => console.log(json));

        // fetch('/api/getTeams')
        //     .then((json) => {
        //         this.userList = json.response.body;
        //         console.log(this.userList);
        //     })
        //     .catch((ex) => {
        //         console.log('parsing failed', ex);
        //     });
        
        this.getTeams();

    }

    onResume() {
        this.getTeams();
        
    }

    getTeams() {
        fetch('/api/team/'+window.localStorage.teamid)
        .then(function(response) {
           console.log(response)
           return response.json()
        }).then(function(json) {
           this.teamDetails = json.data
           this.boardDetails = json.data.boards;
           this.currentboardid = json.data.boards? json.data.boards[0].boardId: ""
           this.currentBoard = json.data.boards? json.data.boards[0].boardDesc: ""
           this.render();
        }.bind(this)).catch(function(ex) {
           console.log('parsing failed', ex)
      })
    }
    /**
     * Render the <e-app-1> app. This function is called each time a
     * prop changes.
     */
    render() {

        

        const { EUI } = window;
       
        const userTable = this.userList.map(user => html`
                            <eui-base-v0-checkbox name="${user.userid}">
                            <div>Name : ${user.username}</div>
                            <div>Id : ${user.userid}</div> </eui-base-v0-checkbox><p>
            `);
        const addTeamForm = html`
            <div slot="content" class="teamForm">
                <div class="field">
                    <eui-base-v0-text-field
                            id="teamName"
                            labeltext="Team Name"
                            @input="${event => this.teamName = event.detail.value}"
                            fullwidth></eui-base-v0-text-field>
                </div>
                <p>
                <div>
                        ${userTable}
                </div>

                <eui-base-v0-button primary slot="footer"
                                    @click="${event => this._createTeamButtonOnClick()}">Create
                </eui-base-v0-button>
            </div>`;



        const boardItems = this.boardDetails
                        .map(item => html`<eui-base-v0-menu-item id=${item.boardId} label=${item.boardDesc}>
                            </eui-base-v0-menu-item>`);

       
        return html`
        
        <div class="padding-left padding-top-20">
            <h3 class="float-left">Boards:</h3>
            <eui-base-v0-dropdown class="button-main-class padding-left" label="${this.currentBoard}" data-type="single"
                @eui-dropdown:click="${(event) => {
                    this.currentboardid= undefined;
                    this.currentboardid = event.detail.menuItems[0].id;
                    console.log(event.detail.menuItems)}}">
            ${boardItems}
            </eui-base-v0-dropdown>
        </div>
        
   
            <!--// <eui-base-v0-button slot="action"
            //                     @click="${event => this.displayCreateTeamFlyout = !this.displayCreateTeamFlyout}">
            //     Create Team
            // </eui-base-v0-button>
            // <eui-base-v0-button slot="action"
            //                     @click="${event => this.displayEditTeamFlyout = !this.displayEditTeamFlyout}">
            //     Edit Existing Team
            // </eui-base-v0-button>
            // <eui-layout-v0-flyout-panel panel-title="Create Team"
            //                             ?show=${this.displayCreateTeamFlyout}>${addTeamForm}
            // </eui-layout-v0-flyout-panel>
            // <eui-layout-v0-flyout-panel panel-title="Edit Existing Team"
            //                             ?show=${this.displayEditTeamFlyout}></eui-layout-v0-flyout-panel>

             // -->
            <div class="padding-left padding-top-40">
            <e-story-table teamdetails=${this.teamDetails} currentboardid=${this.currentboardid}></e-story-table></div>
        `;
    
    }

    _createTeamButtonOnClick() {
        fetch('/api/createTeam', {
            headers: {
                'Content-type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({teamName: this.teamName})
        }).then((json) => {
            console.log(json);
        }).catch((ex) => {
            console.log('parsing failed', ex);

            // apiHelper.post('/api/createTeam', {
            //     teamName: this.teamName
            // }).then(json => console.log(json));
            //
        });

        this.displayCreateTeamFlyout = !this.displayCreateTeamFlyout;
    }

    _createItemButtonOnClick() {
        var jsonToSend = JSON.stringify({
            itemType: this._getIssueType(),
            comment: this._getComment(),
            vote: 1,
            boardId:2
        });
        fetch('/addItem', {
        method : "POST",
        body : jsonToSend
        })
        .then(json => console.log(json));
        this.displayCreateItemFlyout = !this.displayCreateItemFlyout;
    }
    _getComment() {
        const el = this.shadowRoot.getElementById('assignee');
        return el.value || 'Empty';
    }
    _getIssueType() {
        const el = this.shadowRoot.getElementById('issueType');
        return el.value || 'Empty';
    }
    _getDescription() {
        const el = this.shadowRoot.getElementById('description');
        return el.value || 'Empty';
    }
    _getPriority() {
        const el = this.shadowRoot.getElementById('priority');
        return el.value || 'Empty';
    }
}

/**
 * Register the component as e-app-1.
 * Registration can be done at a later time and with a different name
 * Uncomment the below line to register the App if used outside the container
 */
// App1.register();
