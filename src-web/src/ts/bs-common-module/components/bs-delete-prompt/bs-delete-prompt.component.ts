import { Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange } from '@angular/core'




@Component({
  selector: 'bs-delete-prompt',
  templateUrl: './bs-delete-prompt.component.html',
  styleUrls: ['./bs-delete-prompt.component.css']
})
export class BSDeletePrompt implements OnInit {

    @Input() item : any = {}

    @Output() confirmDel: any = new EventEmitter<number>()


    isShowButton : boolean = false

    constructor(
        private el: ElementRef
    ) {

    }



    ngOnInit(): void {
        // console.log('ngOnInit')
    }


    showDeleteButton () {
        this.isShowButton = !this.isShowButton
    }

    confirmDelete () {
        this.isShowButton = false
        this.confirmDel.emit({deleted : true})
    }

}
