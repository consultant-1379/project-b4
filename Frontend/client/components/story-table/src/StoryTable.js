/**
 * Component StoryTable is defined as
 * `<e-story-table>`
 *
 * Imperatively create component
 * @example
 * let component = new StoryTable();
 *
 * Declaratively create component
 * @example
 * <e-story-table></e-story-table>
 *
 * @extends {LitComponent}
 */
import { definition } from '@eui/component';
import { LitComponent, html } from '@eui/lit-component';
import style from './storyTable.css';
import '@eui/layout';
import '@eui/draggable';


/**
 * @property {Boolean} propOne - show active/inactive state.
 * @property {String} propTwo - shows the "Hello World" string.
 */
@definition('e-story-table', {
  style,
  home: 'story-table',
  props: {
    response: { attribute: false },
    body: { attribute: false, type: "string"},
    item_list: { attribute: false, type: "array", default: []},
    cardList:{ attribute: false, type: "array", default: []},
    todoCards:{ attribute: false, type: "string", default: ""},
    boards:{attribute: false, type: "array", default:[]},
    currentboardid: {attribute: true , type:"string", default: 1},
    teamdetails : {attribute: true, type: "array", default: []},
    displayCreateItemFlyout: {attribute: false, type: 'boolean', default: false},
    comment_list: {attribute: false, type: "array", default: []},
    displayCommentFlyout: {attribute: false, type: "boolean", default: false},
    currentItem: {attribute: false}
  },
})
export default class StoryTable extends LitComponent {
  /**
   * Render the <e-story-table> component. This function is called each time a
   * prop changes.
   */
   constructor(){
         super();

          var storage = window.localStorage;
          console.log("Team id : ", storage.teamid);

          // this._getTeamDetails(storage.teamid);


   }

  //  _getTeamDetails(teamId) {
  //    let url = '/api/team/' + teamId;
  //    fetch(url)
  //      .then(function(response) {
  //         console.log(response)
  //         return response.json()
  //      }).then(function(json) {
  //         this.teamDetails = json.data
  //         this.boards = this.teamDetails.boards
  //         console.log("Team : ", this.teamDetails)
  //         console.log("Boards : ", this.boards)
  //         this.currentBoardId = this.boards[this.boards.length - 1].boardId
  //         console.log("current board id : ", this.currentBoardId)
  //         this._getItemList();
  //      }.bind(this)).catch(function(ex) {
  //         console.log('parsing failed', ex)
  //    })
  //  }

   didChangeProps(changedProps) {
    if (changedProps.has('currentboardid')) {
      this._getItemList();
    }
    if (changedProps.has('item_list')) {
      console.log("item_list has been updated");
      console.log("item_list is ", this.item_list);
      this.render();
    }
    if (changedProps.has('comment_list')){
      console.log("comment_list has been updated");
      this.render();
    }
  }
  


   async _getItemList() {
      if(this.currentboardid){
       fetch('/api/getItems?boardId='+(this.currentboardid?this.currentboardid:"1"))
         .then(function(response) {
            return response.json()
         }).then(function(json) {
           console.log(json)
           this.item_list = json.data.items
           if (!this.currentItem) {
             this.currentItem = this.item_list[0]
           }
           console.log("item list is : ", this.item_list)
           return this.item_list
         }.bind(this)).catch(function(ex) {
            console.log('parsing failed', ex)
       })
      }
   }

   async _upvoteItem(item) {
     console.log(item)
     let url = "/api/upvoteItem?itemId=" + item.id;
     fetch(url, {method:'PUT'})
       .then(response => {console.log("this is the response : ", response)})

     await this._getItemList()
   }

   async _downvoteItem(item) {
     console.log(item)
     let url = "/api/downvoteItem?itemId=" + item.id;
     fetch(url, {method:'PUT'})
       .then(response => {console.log("this is the response : ", response)})
     await this._getItemList()
   }

   _dragEvent(event) {
     if (event.type === 'add') {
         console.log(`Card Title: ${event.item.cardTitle}`);
         // Card Title: Card One

         console.log(event.item.class);

         console.log(`Title of destination drop area: ${event.to.dropAreaTitle}`);
         // Title of destination drop area: Drop Area Two

         let itemId = event.item.cardTitle.split(":")[1]
         console.log( parseInt(itemId))
         let itemType = ""
         if (event.to.dropAreaTitle === "Retrospective Item") {
            itemType = "new";
         } else {
            itemType = event.to.dropAreaTitle;
         }
         console.log(itemType)
         this._updateItemType(parseInt(itemId), itemType);
         console.log("class list: ", event.item.classList);
         if (event.item.classList.length !== 0) {
            event.item.classList.remove(`${event.from.dropAreaTitle.toLowerCase()}-card`);
         }
         if (event.to.dropAreaTitle !== "Retrospective Item") {
            event.item.classList.add(`${event.to.dropAreaTitle.toLowerCase()}-card`);
         }
         console.log("class list: ", event.item.classList);
     }
   }

   _updateItemType(itemId, itemType) {
     let url = `/api/updateItemType?itemId=${itemId}&itemType=${itemType}`;
     console.log(url)
     fetch(url, {method:'PUT', headers:{'Accept': 'application/json'} })
       .then(response => {
       return response.json();
     })
     .then(json => { json.data})
     .then(item => {
        console.log("item : ", item)
        for (let each in this.item_list) {
            console.log("each is ", this.item_list[each].id)
            if (this.item_list[each].id === itemId) {
                this.item_list[each].itemType = itemType
            }
        }
        console.log("the updated list : ", this.item_list)
     })
   }

  

  render() {
    var self = this;
    console.log("Board ID"+this.currentboardid)

    const addItemForm = html`
            <div slot="content" class="teamForm">

                <p>
                <div class="field">
                    <eui-base-v0-text-field id="summary" labeltext="Summary"
                                            fullwidth></eui-base-v0-text-field>
                </div>
                <p>
                <div class="field">
                    <eui-base-v0-text-field id="description" labeltext="Description"
                                            fullwidth></eui-base-v0-text-field>
                </div>
                <p>
                <div class="field">
                    <eui-base-v0-text-field id="priority" labeltext="Priority"
                                            fullwidth></eui-base-v0-text-field>
                </div>
                <p>

                <eui-base-v0-button primary slot="footer"
                                    @click="${event => this._createItemButtonOnClick()}">Create
                </eui-base-v0-button>
                <eui-base-v0-button style="float:right;" @click="${event => this.displayCreateItemFlyout = !this.displayCreateItemFlyout}">Cancel</eui-base-v0-button>
            </div>`;

    const backlogItems = this.item_list
                        .filter(item => item.itemType === "new")
                        .sort((a, b) => {
                        if (a.id > b.id) { return 1; }
                        if (a.id < b.id) { return -1; }
                        return 0;
                        })
                        .map(item => html`<eui-layout-v0-card card-title="Item ID : ${item.id}">
                                <div slot="content">Item Summary : ${item.summary}<p>Votes : ${item.vote}<p>Priority : ${item.priority}</div>
                                <eui-base-v0-tooltip slot="action" message="Upvote" position="top">
                                    <eui-v0-icon name="plus"  @click=${(event) => {
                                      event.stopPropagation();
                                      console.log('upvote action clicked');
                                      const upvoteAction = async () => {
                                        console.log("Before await function")
                                        await this._upvoteItem(item);
                                      }
                                      upvoteAction();
                                    }}>
                                    </eui-v0-icon>
                                  </eui-base-v0-tooltip>
                                  <eui-base-v0-tooltip slot="action" message="Downvote" position="top">
                                    <eui-v0-icon name="minus" @click=${(event) => {
                                      event.stopPropagation();
                                      console.log('downvote action clicked');
                                      const downvoteAction = async () => {
                                        console.log("Before await function")
                                        await this._downvoteItem(item);
                                      }
                                      downvoteAction();
                                    }}>
                                    </eui-v0-icon>
                                  </eui-base-v0-tooltip>
                                  <eui-base-v0-tooltip slot="action" message="Comments" position="top">
                                    <eui-v0-icon name="edit" @click=${(event) => {
                                      event.stopPropagation();
                                      this.displayCommentFlyout = !this.displayCommentFlyout;
                                      this.currentItem = item;
                                      this.comment_list = item.comments;
                                    }}>
                                    </eui-v0-icon>
                                  </eui-base-v0-tooltip>
                                </eui-layout-v0-card>`);

    const madItems = this.item_list
                        .filter(item => item.itemType === "Mad")
                        .sort((a, b) => {
                        if (a.id > b.id) { return 1; }
                        if (a.id < b.id) { return -1; }
                        return 0;
                        })
                        .map(item => html`<eui-layout-v0-card class="mad-card" card-title="Item ID : ${item.id}">
                                <div slot="content">Item Summary : ${item.summary}<p>Votes : ${item.vote}<p>Priority : ${item.priority}</div>
                                <eui-base-v0-tooltip slot="action" message="Upvote" position="top">
                                    <eui-v0-icon name="plus"  @click=${(event) => {
                                      event.stopPropagation();
                                      console.log('upvote action clicked');
                                      const upvoteAction = async () => {
                                        console.log("Before await function")
                                        await this._upvoteItem(item);
                                      }
                                      upvoteAction();
                                    }}>
                                    </eui-v0-icon>
                                  </eui-base-v0-tooltip>
                                  <eui-base-v0-tooltip slot="action" message="Downvote" position="top">
                                    <eui-v0-icon name="minus" @click=${(event) => {
                                      event.stopPropagation();
                                      console.log('downvote action clicked');
                                      const downvoteAction = async () => {
                                        console.log("Before await function")
                                        await this._downvoteItem(item);
                                      }
                                      downvoteAction();
                                    }}>
                                    </eui-v0-icon>
                                  </eui-base-v0-tooltip>
                                  <eui-base-v0-tooltip slot="action" message="Comments" position="top">
                                      <eui-v0-icon name="edit" @click=${(event) => {
                                        event.stopPropagation();
                                        this.displayCommentFlyout = !this.displayCommentFlyout;
                                        this.currentItem = item;
                                        this.comment_list = item.comments;
                                      }}>
                                      </eui-v0-icon>
                                    </eui-base-v0-tooltip>
                                </eui-layout-v0-card>`);

    const sadItems = this.item_list
                            .filter(item => item.itemType === "Sad")
                            .sort((a, b) => {
                            if (a.id > b.id) { return 1; }
                            if (a.id < b.id) { return -1; }
                            return 0;
                            })
                            .map(item => html`<eui-layout-v0-card class="sad-card" card-title="Item ID : ${item.id}">
                                    <div slot="content">Item Summary : ${item.summary}<p>Votes : ${item.vote}<p>Priority : ${item.priority}</div>
                                    <eui-base-v0-tooltip slot="action" message="Upvote" position="top">
                                        <eui-v0-icon name="plus"  @click=${(event) => {
                                          event.stopPropagation();
                                          console.log('upvote action clicked');
                                          const upvoteAction = async () => {
                                            console.log("Before await function")
                                            await this._upvoteItem(item);
                                          }
                                          upvoteAction();
                                        }}>
                                        </eui-v0-icon>
                                      </eui-base-v0-tooltip>
                                      <eui-base-v0-tooltip slot="action" message="Downvote" position="top">
                                        <eui-v0-icon name="minus" @click=${(event) => {
                                          event.stopPropagation();
                                          console.log('downvote action clicked');
                                          const downvoteAction = async () => {
                                            console.log("Before await function")
                                            await this._downvoteItem(item);
                                          }
                                          downvoteAction();
                                        }}>
                                        </eui-v0-icon>
                                      </eui-base-v0-tooltip>
                                      <eui-base-v0-tooltip slot="action" message="Comments" position="top">
                                        <eui-v0-icon name="edit" @click=${(event) => {
                                          event.stopPropagation();
                                          this.displayCommentFlyout = !this.displayCommentFlyout;
                                          this.currentItem = item;
                                          this.comment_list = item.comments;
                                        }}>
                                        </eui-v0-icon>
                                      </eui-base-v0-tooltip>
                                    </eui-layout-v0-card>`);

    const gladItems = this.item_list
                            .filter(item => item.itemType === "Glad")
                            .sort((a, b) => {
                            if (a.id > b.id) { return 1; }
                            if (a.id < b.id) { return -1; }
                            return 0;
                            })
                            .map(item => html`<eui-layout-v0-card class="glad-card" card-title="Item ID : ${item.id}">
                                    <div slot="content">Item Summary : ${item.summary}<p>Votes : ${item.vote}<p>Priority : ${item.priority}</div>
                                    <eui-base-v0-tooltip slot="action" message="Upvote" position="top">
                                        <eui-v0-icon name="plus"  @click=${(event) => {
                                          event.stopPropagation();
                                          console.log('upvote action clicked');
                                          const upvoteAction = async () => {
                                            console.log("Before await function")
                                            await this._upvoteItem(item);
                                          }
                                          upvoteAction();
                                        }}>
                                        </eui-v0-icon>
                                      </eui-base-v0-tooltip>
                                      <eui-base-v0-tooltip slot="action" message="Downvote" position="top">
                                        <eui-v0-icon name="minus" @click=${(event) => {
                                          event.stopPropagation();
                                          console.log('downvote action clicked');
                                          const downvoteAction = async () => {
                                            console.log("Before await function")
                                            await this._downvoteItem(item);
                                          }
                                          downvoteAction();
                                        }}>
                                        </eui-v0-icon>
                                      </eui-base-v0-tooltip>
                                      <eui-base-v0-tooltip slot="action" message="Comments" position="top">
                                        <eui-v0-icon name="edit" @click=${(event) => {
                                          event.stopPropagation();
                                          this.displayCommentFlyout = !this.displayCommentFlyout;
                                          this.currentItem = item;
                                          this.comment_list = item.comments;
                                        }}>
                                        </eui-v0-icon>
                                      </eui-base-v0-tooltip>
                                    </eui-layout-v0-card>`);

    const backlogDragArea = html`
        <eui-draggable-v0-drop-area
            border
            drop-area-title="Retrospective Item"
            @add=${event => {this._dragEvent(event)}}
            class="backlog">${backlogItems}
        </eui-draggable-v0-drop-area>`;




    const toDoDragArea = html`<eui-draggable-v0-drop-area
                                      border
                                      drop-area-title="Mad"
                                      description="Items that are mad."
                                      @add=${event => {this._dragEvent(event)}}
                                      class="sprint">${madItems}
                                  </eui-draggable-v0-drop-area>`;

    const inProgressDragArea = html`<eui-draggable-v0-drop-area
            border
            drop-area-title="Sad"
            description="Items that are sad."
            @add=${event => {this._dragEvent(event)}}
            class="sprint">${sadItems}
        </eui-draggable-v0-drop-area>`;

    const doneDragArea = html`<eui-draggable-v0-drop-area
                                      border
                                      drop-area-title="Glad"
                                      description="Items that are okay."
                                      @add=${event => {this._dragEvent(event)}}
                                      class="sprint">${gladItems}
                                  </eui-draggable-v0-drop-area>`;


    const comments = this.comment_list.map(item => html`<div slot="content">
        <eui-v0-icon name="avatar" style="margin-right:5%;"></eui-v0-icon>${item}</div><p slot="content">`);
    const emptyComments = html`<div slot="content">
       <eui-v0-icon name="avatar" style="margin-right:5%;"></eui-v0-icon>There are no comments.</div><p slot="content">`;

    return html`
    <eui-base-v0-button primary slot="action"
      @click="${event => this.displayCreateItemFlyout = !this.displayCreateItemFlyout}">
         Create Item
    </eui-base-v0-button>
    <eui-layout-v0-flyout-panel panel-title="Create Item"
      ?show=${this.displayCreateItemFlyout}>${addItemForm}
    </eui-layout-v0-flyout-panel>
    <p>
    <h3 class="backlog">Retrospective Items</h3>
    <h3 class="sprint">Mad &#128544;</h3>
    <h3 class="sprint">Sad &#128542;</h3>
    <h3 class="sprint">Glad &#128516;</h3>
    ${backlogDragArea}
    ${toDoDragArea}
    ${inProgressDragArea}
    ${doneDragArea}
    <eui-layout-v0-flyout-panel panel-title="Comments"
          ?show=${this.displayCommentFlyout}>
          <div slot="content"><b>Summary : </b>${this.currentItem?this.currentItem.summary:"empty"}</div><p slot="content">
          <div slot="content"><b>Description : </b>${this.currentItem?this.currentItem.description:"empty"}</div><p slot="content">
          <div slot="content"><b>Priority : </b>${this.currentItem?this.currentItem.priority:"empty"}</div><p slot="content">

          <div slot="content"><b><u>Comments </u></b></div><p slot="content">
          ${this.comment_list.length === 0?emptyComments:comments}
          <div slot="content">
          <eui-base-v0-textarea id="newComment" fullwidth></eui-base-v0-textarea>
          </div>
          <p slot="content">
          <div slot="content">
          <eui-base-v0-button primary slot="action" @click="${event => this._addCommentButtonClick()}">Add Comment</eui-base-v0-button>
          <eui-base-v0-button style="float:right;" @click="${event => this.displayCommentFlyout = !this.displayCommentFlyout}">Cancel</eui-base-v0-button>
          </div>
    </eui-layout-v0-flyout-panel>
    `;
  }

  _addCommentButtonClick() {
    let url = `/api/addComments?itemId=${this.currentItem.id}&comment=${this._getNewComment()}`;
    let jsonToSend = JSON.stringify({comment: this._getNewComment()});
    console.log(jsonToSend);
    fetch(url, {
      method : "PUT",
      headers: {
            'Accept': 'application/json'
      }
      })
    .then(json => {console.log(json); this.comment_list.push(this._getNewComment()); this.render(); this._clearTextFieldById('newComment');});

    this.displayCommentFlyout = !this.displayCommentFlyout;

  }

  _createItemButtonOnClick() {
      var jsonToSend = JSON.stringify({
          summary: this._getSummary(),
          itemType: "new",
          description: this._getDescription(),
          vote: "1",
          priority: this._getPriority(),
          boardId: parseInt(this.currentboardid)
      });
      console.log(jsonToSend)
      fetch('/api/addItem', {
      method : "POST",
      headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
      },
      body : jsonToSend
      })
      .then(json => {console.log(json); this._getItemList(); this._clearTextFieldById('summary'); this._clearTextFieldById('description'); this._clearTextFieldById('priority');});
      this.displayCreateItemFlyout = !this.displayCreateItemFlyout;
  }
  _getSummary() {
      const el = this.shadowRoot.getElementById('summary');
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

  _getNewComment() {
      const el = this.shadowRoot.getElementById('newComment');
      return el.value || 'Empty';
  }

  _clearTextFieldById(id) {
      const el = this.shadowRoot.getElementById(id);
      el.value = "";
  }
}

/**
 * Register the component as e-story-table.
 * Registration can be done at a later time and with a different name
 */
StoryTable.register();
