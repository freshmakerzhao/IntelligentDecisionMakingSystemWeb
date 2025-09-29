<template>
    <div>
        <div style="margin: 10px 0">
            <!-- 搜索框 suffix-icon内嵌图标 primary蓝色按钮 v-model绑定参数-->
            <el-input style="width: 200px" placeholder="请输入发帖人" suffix-icon="el-icon-search" v-model="userNickname"></el-input>
            <el-input style="width: 200px" class="ml-5" placeholder="请输入物品名称" suffix-icon="el-icon-search" v-model="name"></el-input>
            <el-input style="width: 200px" class="ml-5"  placeholder="请输入描述关键字" suffix-icon="el-icon-search" v-model="content"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <!--        tableData存储表格数据-->
        <!--        header-cell-class-name设置固定表格样式-->
        <!--        selection-change选择时触发-->
        <!--        header-cell-style 表头样式-->
        <!--        :cell-style 单元格样式-->
        <!--        :row-style 行样式-->
        <el-table
                :data="tableData"
                border
                stripe
                :header-cell-class-name="'headerBg'"
                :header-cell-style="{'text-align':'center'}"
                :cell-style="{'text-align':'center'}"
                :row-style="{height:'80px'}"
        >

            <el-table-column type="expand">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="购入价格：">
                            <span>{{ props.row.buyValue ? props.row.buyValue + " 元" : "/"}}</span>
                        </el-form-item>
                        <el-form-item label="当前价格：">
                            <span>{{ props.row.curValue ? props.row.curValue + " 元" : "/"}}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="ID" width="35"></el-table-column>
            <el-table-column prop="userNickname" label="发帖人" width="120"></el-table-column>
            <el-table-column prop="contact" label="联系方式" width="120"></el-table-column>
            <el-table-column prop="likeNums" sortable label="点赞数" width="85"></el-table-column>
            <el-table-column prop="browseNums" sortable label="浏览数"  width="85"></el-table-column>
            <el-table-column prop="name" sortable label="物品名称"  width="85"></el-table-column>
            <el-table-column label="物品描述">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top" width="300">
                        <p>{{scope.row.content}}</p>
                        <div slot="reference">
                            <span class="ellipsis">{{scope.row.content}}</span>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" sortable width="160"label="发布时间"  :formatter="formatDate"></el-table-column>
            <el-table-column label="操作"  width="100" align="center">
                <template slot-scope="scope">
                    <!--删除-->
                    <el-popconfirm
                            class="ml-5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="您确定操作吗？"
                            @confirm="del(scope.row.id)"
                    >
                        <el-button v-if="scope.row.isExpire !== 1" type="danger" slot="reference">失效 <i class="el-icon-remove-outline"></i></el-button>
                        <el-button v-else type="info" disabled slot="reference">失效 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <!--              底部页码等信息-->
        <!--              @size-change="handleSizeChange" 动态绑定，获取页面显示条数-->
        <!--              @current-change="handleCurrentChange" 动态绑定，获取页面所在页数-->
        <!--              :current-page="pageNum" 动态显示当前页-->
        <!--              :page-sizes="[5, 10, 15, 20]" 设置每页显示个数-->
        <!--              :page-size="10" 默认显示个数-->
        <!--              layout="total, sizes, prev, pager, next, jumper" 从左到右顺序-->
        <!--              :total="400" 总个数-->
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[2, 5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return{
                user: JSON.parse(localStorage.getItem("user")), // 存储当前登录用户信息
                tableData: [],
                total: 0,
                pageNum: 1, // 当前页，默认为1
                pageSize: 5, // 每页显示数量，默认5
                userNickname: '', // 搜索
                name: '', // 搜索
                content: '', // 搜索
            }
        },
        created() { // 加载时触发
            this.load();
        },
        methods:{
            // 从后台获取数据，并在页面中赋值
            async load() {
                await this.request.get("/secondHand/page", {
                    // 对应后台参数名称，将页面默认信息传到后台，获取表中数据
                    params: {
                        pageNum: this.pageNum,
                        pageSize: this.pageSize,
                        userNickname: this.userNickname,
                        name: this.name,
                        content: this.content,
                    }
                }).then(res => {
                    // 获取到数据后，request打包，返回前台
                    this.tableData = res.data.records;
                    this.total = res.data.total;
                });
            },
            // 失效触发
            del(id) {
                this.request.delete("/secondHand/del/" + id).then(res => {
                    if (res.code === "200") {
                        this.$message.success("操作成功");
                        this.load();
                    } else {
                        this.$message.error("操作失败");
                    }
                })
            },
            // 重置搜索框
            reset() {
                this.userNickname = "";
                this.name = "";
                this.content = "";
                // 重置搜索框后，重新加载表格
                this.load();
            },
            //  改变当前页码
            handleCurrentChange(pageNum) {
                this.pageNum = pageNum;
                this.load();
            },
            //  改变当前每页容量
            handleSizeChange(pageSize) {
                this.pageSize = pageSize;
                this.load();
            },
            // 格式化空内容
            formatEmpty(row, column) {
                // 获取单元格数据
                let temp = row[column.property];
                if(temp == null) {
                    return "/";
                }else{
                    return temp;
                }
            },
            // 时间格式化
            formatDate(row, column) {
                // 获取单元格数据
                let data = row[column.property];
                if(data === null) {
                    return "/";
                }
                if(data.length < 1) {
                    return "/";
                }
                let dt = new Date(data);
                return dt.getFullYear() + '年' + (dt.getMonth() + 1) + '月' + dt.getDate() + '日' + dt.getHours() + '时' + dt.getMinutes() + '分';
            },
        }
    }

</script>

<style>
    .headerBg {
        background: #eee!important;
    }
    /*详情条*/
    .demo-table-expand {
        font-size: 0;
    }
    /*详情条标题字体*/
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
        display: block!important;
    }
    /*详情条内容各占一行*/
    .demo-table-expand span {
        margin-left: 20px;
        display: block!important;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        display: block;
    }
    /*去掉悬浮显示*/
    .el-tooltip__popper{
        display: none;
    }
    .ellipsis {
        display:-webkit-box;
        text-overflow:ellipsis;
        overflow:hidden;
        -webkit-line-clamp: 2;
        -webkit-box-orient:vertical;
    }
    .el-popover{
        height: auto;
        color: #fff;
        background: rgba(0, 0, 0, 0.8);
    }
</style>