# Memory Bank: Travel App Android

## Overview
This Memory Bank serves as the complete knowledge base for the Travel App Android project. Due to memory resets between sessions, this documentation is **critical** for maintaining project continuity and understanding.

## 🔥 CRITICAL WORKFLOW RULE
**After EVERY commit, the Memory Bank MUST be reviewed and updated with any significant changes.**

This ensures:
- Complete session continuity
- Accurate project state tracking
- Proper documentation of all decisions and changes
- Effective knowledge transfer between development sessions

## File Structure
```
memory-bank/
├── README.md           # This file - explains the system
├── projectbrief.md     # Foundation document - project scope and goals
├── productContext.md   # Product vision and user needs
├── activeContext.md    # Current work focus and recent changes
├── systemPatterns.md   # Architecture and technical decisions
├── techContext.md      # Technologies and development setup
└── progress.md         # Status tracking and evolution
```

## Core Files (Must Read Every Session)

### 1. projectbrief.md
- **Purpose**: Foundation document that shapes all others
- **Contains**: Core requirements, goals, key decisions
- **When to Update**: When project scope or fundamental requirements change

### 2. productContext.md
- **Purpose**: Product vision and user experience goals
- **Contains**: Why the project exists, problems it solves, target audience
- **When to Update**: When product direction or user needs evolve

### 3. activeContext.md
- **Purpose**: Current work focus and immediate context
- **Contains**: Recent changes, next steps, active decisions
- **When to Update**: Most frequently - after significant changes or decisions

### 4. systemPatterns.md
- **Purpose**: Technical architecture and implementation patterns
- **Contains**: System design, component relationships, critical paths
- **When to Update**: When architectural decisions are made or patterns emerge

### 5. techContext.md
- **Purpose**: Technical stack and development environment
- **Contains**: Technologies, tools, dependencies, constraints
- **When to Update**: When adding new technologies or changing development setup

### 6. progress.md
- **Purpose**: Project status and evolution tracking
- **Contains**: What works, what's left, milestones, technical debt
- **When to Update**: Regularly - tracks project evolution and current state

## Usage Guidelines

### At Session Start
1. **Always** read ALL core files before beginning work
2. Focus on `activeContext.md` and `progress.md` for current state
3. Review `projectbrief.md` to understand project foundation

### During Work
1. Document decisions and patterns as they emerge
2. Update `activeContext.md` with progress and insights
3. Note any architectural decisions in `systemPatterns.md`

### 🔥 MANDATORY: After Every Commit
1. **Review the commit changes** - understand what was added/modified
2. **Update relevant Memory Bank files** with new information:
   - `activeContext.md` - Recent changes and new focus areas
   - `progress.md` - Completed tasks and updated status
   - `systemPatterns.md` - New architectural decisions or patterns
   - `techContext.md` - New dependencies or technical changes
   - `projectbrief.md` - If project direction evolved
   - `productContext.md` - If features or user needs changed
3. **Commit the Memory Bank updates** with descriptive message
4. **Verify all significant changes are documented** for future sessions

### When Requested "update memory bank"
1. **Must** review ALL files, even if unchanged
2. Focus particularly on `activeContext.md` and `progress.md`
3. Document current state, insights, and next steps

## Workflow Integration
```
Development Work → Git Commit → Review Changes → Update Memory Bank → Commit Memory Bank Updates → Continue Development
```

## Project Status
- **Created**: During initial project setup
- **Current Phase**: UI Foundation established, architecture documented
- **Next Steps**: Core feature implementation with comprehensive documentation

## Key Reminders
- **Never skip Memory Bank updates** after commits
- **Memory Bank accuracy is critical** for session continuity
- **Document decisions immediately** while context is fresh
- **All architectural and technical changes must be captured**

## Project Status
- **Created**: During initial project setup
- **Current Phase**: Project initialization complete, ready for planning
- **Next Steps**: Define travel app requirements and features

## Key Reminders
- This is a fresh Android project with no custom features yet
- Memory Bank system is now established for session continuity
- All core documentation files are in place and ready for updates 