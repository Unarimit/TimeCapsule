using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace TimeCapsule.Infrastructure.Persistence.Migrations
{
    public partial class deletemodels : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "SubTaskRecords");

            migrationBuilder.DropTable(
                name: "SubTasks");

            migrationBuilder.DropColumn(
                name: "DeadLine",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "IsCounting",
                table: "Tasks");

            migrationBuilder.DropColumn(
                name: "TotalTime",
                table: "Tasks");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<DateTime>(
                name: "DeadLine",
                table: "Tasks",
                type: "datetime(6)",
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "IsCounting",
                table: "Tasks",
                type: "tinyint(1)",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<TimeSpan>(
                name: "TotalTime",
                table: "Tasks",
                type: "time(6)",
                nullable: false,
                defaultValue: new TimeSpan(0, 0, 0, 0, 0));

            migrationBuilder.CreateTable(
                name: "SubTaskRecords",
                columns: table => new
                {
                    Id = table.Column<string>(type: "char(36)", nullable: false),
                    IncreProgress = table.Column<int>(type: "int", nullable: false),
                    PeriodId = table.Column<string>(type: "char(36)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_SubTaskRecords", x => x.Id);
                    table.ForeignKey(
                        name: "FK_SubTaskRecords_Periods_PeriodId",
                        column: x => x.PeriodId,
                        principalTable: "Periods",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "SubTasks",
                columns: table => new
                {
                    Id = table.Column<string>(type: "char(36)", nullable: false),
                    Achievement = table.Column<int>(type: "int", nullable: false),
                    IsMulti = table.Column<bool>(type: "tinyint(1)", nullable: false),
                    LastModified = table.Column<DateTime>(type: "datetime(6)", nullable: false),
                    Name = table.Column<string>(type: "varchar(50) CHARACTER SET utf8mb4", maxLength: 50, nullable: false),
                    Progress = table.Column<int>(type: "int", nullable: false),
                    TargetProgress = table.Column<int>(type: "int", nullable: false),
                    TaskId = table.Column<string>(type: "char(36)", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_SubTasks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_SubTasks_Tasks_TaskId",
                        column: x => x.TaskId,
                        principalTable: "Tasks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_SubTaskRecords_PeriodId",
                table: "SubTaskRecords",
                column: "PeriodId");

            migrationBuilder.CreateIndex(
                name: "IX_SubTasks_TaskId",
                table: "SubTasks",
                column: "TaskId");
        }
    }
}
