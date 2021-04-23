using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace TimeCapsule.Infrastructure.Persistence.Migrations
{
    public partial class first_init : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "RegisterRequests",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    LastModified = table.Column<DateTime>(nullable: false),
                    Username = table.Column<string>(maxLength: 40, nullable: false),
                    Password = table.Column<string>(maxLength: 256, nullable: false),
                    Email = table.Column<string>(maxLength: 128, nullable: false),
                    Desc = table.Column<string>(maxLength: 256, nullable: true),
                    Status = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_RegisterRequests", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "Users",
                columns: table => new
                {
                    Username = table.Column<string>(maxLength: 40, nullable: false),
                    LastModified = table.Column<DateTime>(nullable: false),
                    Password = table.Column<string>(maxLength: 256, nullable: false),
                    Email = table.Column<string>(maxLength: 50, nullable: false),
                    IsAdmin = table.Column<bool>(nullable: false),
                    IsUser = table.Column<bool>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Users", x => x.Username);
                });

            migrationBuilder.CreateTable(
                name: "Dailies",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    LastModified = table.Column<DateTime>(nullable: false),
                    Username = table.Column<string>(nullable: false),
                    Desc = table.Column<string>(maxLength: 2048, nullable: true),
                    Calendar = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Dailies", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Dailies_Users_Username",
                        column: x => x.Username,
                        principalTable: "Users",
                        principalColumn: "Username",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "TaskClasses",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    LastModified = table.Column<DateTime>(nullable: false),
                    Name = table.Column<string>(maxLength: 40, nullable: false),
                    Color = table.Column<string>(maxLength: 10, nullable: false),
                    Username = table.Column<string>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_TaskClasses", x => x.Id);
                    table.ForeignKey(
                        name: "FK_TaskClasses_Users_Username",
                        column: x => x.Username,
                        principalTable: "Users",
                        principalColumn: "Username",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Tasks",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    LastModified = table.Column<DateTime>(nullable: false),
                    Name = table.Column<string>(maxLength: 50, nullable: false),
                    Desc = table.Column<string>(maxLength: 256, nullable: true),
                    Icon = table.Column<string>(maxLength: 512, nullable: false),
                    AchievementPerHour = table.Column<double>(nullable: false),
                    IsFinish = table.Column<bool>(nullable: false),
                    IsOften = table.Column<bool>(nullable: false),
                    BeginTime = table.Column<DateTime>(nullable: false),
                    FinishTime = table.Column<DateTime>(nullable: true),
                    Username = table.Column<string>(nullable: false),
                    TaskClassId = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Tasks", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Tasks_TaskClasses_TaskClassId",
                        column: x => x.TaskClassId,
                        principalTable: "TaskClasses",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Tasks_Users_Username",
                        column: x => x.Username,
                        principalTable: "Users",
                        principalColumn: "Username",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "Periods",
                columns: table => new
                {
                    Id = table.Column<Guid>(nullable: false),
                    LastModified = table.Column<DateTime>(nullable: false),
                    IsFinish = table.Column<bool>(nullable: false),
                    BeginTime = table.Column<DateTime>(nullable: false),
                    EndTime = table.Column<DateTime>(nullable: true),
                    TaskId = table.Column<Guid>(nullable: false),
                    DailyId = table.Column<Guid>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Periods", x => x.Id);
                    table.ForeignKey(
                        name: "FK_Periods_Dailies_DailyId",
                        column: x => x.DailyId,
                        principalTable: "Dailies",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_Periods_Tasks_TaskId",
                        column: x => x.TaskId,
                        principalTable: "Tasks",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Dailies_Username",
                table: "Dailies",
                column: "Username");

            migrationBuilder.CreateIndex(
                name: "IX_Periods_DailyId",
                table: "Periods",
                column: "DailyId");

            migrationBuilder.CreateIndex(
                name: "IX_Periods_TaskId",
                table: "Periods",
                column: "TaskId");

            migrationBuilder.CreateIndex(
                name: "IX_TaskClasses_Username",
                table: "TaskClasses",
                column: "Username");

            migrationBuilder.CreateIndex(
                name: "IX_Tasks_TaskClassId",
                table: "Tasks",
                column: "TaskClassId");

            migrationBuilder.CreateIndex(
                name: "IX_Tasks_Username",
                table: "Tasks",
                column: "Username");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Periods");

            migrationBuilder.DropTable(
                name: "RegisterRequests");

            migrationBuilder.DropTable(
                name: "Dailies");

            migrationBuilder.DropTable(
                name: "Tasks");

            migrationBuilder.DropTable(
                name: "TaskClasses");

            migrationBuilder.DropTable(
                name: "Users");
        }
    }
}
