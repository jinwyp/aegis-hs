import {Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChange} from '@angular/core'




@Component({
    selector    : 'bs-pagination',
    templateUrl : './bs-pagination.component.html',
    styleUrls: ['./bs-pagination.component.css']
})
export class BSPaginationComponent implements OnInit, OnChanges {

    _totalPageCount: number = 1

    _pageArrayLeft :  number[] = []
    _pageArrayRight :  number[] = []
    _pageArrayMiddle :  number[] = []


    _ellipsisLeft: boolean = false
    _ellipsisRight: boolean = false

    /**
     *  Number of items in collection.
     */
    @Input() collectionSize: number = 0


    /**
     *  Current page.
     */
    @Input() page: number = 0


    /**
     *  An event fired when the page is changed.
     *  Event's payload equals to the newly selected page.
     */
    @Output() pageChange: any = new EventEmitter<number>()


    /**
     *  Number of items per page.
     */
    @Input() pageSize: number = 10


    /**
     * Pagination display size: small or large
     */
    @Input() size: 'sm' | 'lg'


    /**
     * Pagination display size: small or large
     */
    @Input() alignment: 'left' | 'center' | 'right'



    constructor(
        private el: ElementRef
    ) {
    }

    ngOnInit(): void {

        // console.log('ngOnInit')
        // this.showPages()
    }


    ngOnChanges(changes: { [propKey: string]: SimpleChange }) {
        // console.log('ngOnChanges')

        for (const propertyName in changes) {

            if (changes.hasOwnProperty(propertyName)) {
                const currentChangeObject = changes[propertyName]

                if (currentChangeObject.currentValue && currentChangeObject.isFirstChange) {
                    // console.log('currentChangeObject firstChange: ', currentChangeObject)
                }else {
                    // console.log('currentChangeObject secondChange: ', currentChangeObject)
                }

            }
        }

        this.showPages()
    }


    showPages () {

        // console.log(this.collectionSize, this.page, this.pageSize)


        if (this.collectionSize) {
            this.collectionSize = Number(this.collectionSize)
        }

        if (this.pageSize) {
            this.pageSize = Number(this.pageSize)
        }

        if (this.page) {
            this.page = Number(this.page)
        }

        this._totalPageCount = Math.ceil(this.collectionSize / this.pageSize)


        if (this.page < 1) {
            this.page = 1
        }else if (this.page > this._totalPageCount) {
            this.page = this._totalPageCount
        }



        this._pageArrayLeft = []
        this._pageArrayRight = []
        this._pageArrayMiddle = []

        this._ellipsisLeft = false
        this._ellipsisRight = false


        const paginationShowNumberLimit = 8
        const paginationLeftShowNumber = 2
        const paginationRightShowNumber = 2
        const paginationMiddleShowNumber = 3

        const currentPageShowLeftNumber = paginationMiddleShowNumber + 1
        const currentPageShowMiddleNumber = Math.ceil(paginationMiddleShowNumber / 2)


        for (let i = 1 ; i <= this._totalPageCount; i++) {

            if (this._totalPageCount <= paginationShowNumberLimit) {
                this._pageArrayMiddle.push( i )

            } else {

                //创建左部分的分页 例如 1,2
                if ( i <= paginationLeftShowNumber ) { this._pageArrayLeft.push(i) }


                //创建右部分的分页 例如 99,100
                if ( i >= this._totalPageCount - (paginationRightShowNumber - 1) ) { this._pageArrayRight.push(i) }


                //创建中间部分的分页 例如 49,50,51
                if (i > paginationLeftShowNumber  && i < this._totalPageCount - (paginationRightShowNumber - 1) ) {

                    if (this.page <= currentPageShowLeftNumber && i <= (currentPageShowLeftNumber + 1) ) {
                        this._ellipsisRight = true
                        this._pageArrayMiddle.push( i )
                    }

                    if ( this.page > currentPageShowLeftNumber && this.page < this._totalPageCount - paginationMiddleShowNumber) {
                        this._ellipsisLeft = true
                        this._ellipsisRight = true

                        if ( i > this.page - currentPageShowMiddleNumber && i < this.page + currentPageShowMiddleNumber) {
                            this._pageArrayMiddle.push( i )
                        }
                    }

                    if ( this.page >= this._totalPageCount - paginationMiddleShowNumber && i >= this._totalPageCount - paginationMiddleShowNumber - 1) {
                        this._ellipsisLeft = true
                        this._pageArrayMiddle.push( i )
                    }
                }
            }
        }


    }




    changePage (event: any, pageNo: number, buttonName? : string) {

        event.stopPropagation()

        if ( (buttonName === 'prev' && pageNo === 1)  || (buttonName === 'next' && pageNo === this._totalPageCount) || pageNo === this.page) {
            return  //disabled, active不会触发
        }

        let tempNo: number = Number(pageNo)

        if (tempNo < 1) {
            tempNo = 1

        }else if (tempNo > this._totalPageCount) {
            tempNo = this._totalPageCount
        }

        this.page = tempNo
        this.showPages()

        this.pageChange.emit(this.page)

    }

}
